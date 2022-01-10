package web4.back.tokens;

import io.jsonwebtoken.*;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web4.back.AuthResponse;
import web4.back.db.UserDBService;

import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Date;

@Component
public class TokenManager implements TokensManaging {

    protected static PublicKey publicKey = readPublicKey();
    protected static PrivateKey privateKey = readPrivateKey();

    @Autowired
    private UserDBService userDBService;

    @Override
    public Token generateSmall(String userIdentify) {
        return generate(userIdentify, 5000);
    }

    @Override
    public Token generateAccess(String userIdentify) {
        return generate(userIdentify, 1800000);
    }

    @Override
    public Token generateRefresh(String userIdentify) {
        return generate(userIdentify, 86400000);
    }

    @Override
    public String findUserIdentify(Token token) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token.toString()).getBody().get("userIdentify", String.class);
    }

    @Override
    public AuthResponse updateTokens(Token refreshToken) {
        if (check(refreshToken)) {
            String userId = findUserIdentify(refreshToken);
            if(userDBService.findUserById(new Long(userId)).getRefreshToken().equals(refreshToken.toString())){
                Token newAccessToken = generateAccess(userId);
                Token newRefreshToken = generateRefresh(userId);
                userDBService.addAccessToken(new Long(userId), newAccessToken);
                userDBService.addRefreshToken(new Long(userId), newRefreshToken);
                return new AuthResponse(newAccessToken, newRefreshToken, new Long(userId));
            }
        }
        return null;
    }

    private Token generate(String userIdentify, long period) {
        readPrivateKey();

        JwtBuilder builder = Jwts.builder();
        builder.claim("userIdentify", userIdentify);
        Date date = new Date();
        builder.setIssuedAt(date);
        builder.setExpiration(new Date(date.getTime() + period));
        builder.signWith(privateKey);
        return new Token(builder.compact());
    }

    @Override
    public boolean check(Token token) {
        try {
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token.toString());
//            return State.VALID;
            return true;
        } catch (MalformedJwtException e) {
//            return State.IN_VALID;
            return false;
        } catch (IllegalArgumentException e) {
            //БОЛЬШЕ ДЛЯ ПРОВЕРКИ, ТК ВЫСКАКИВАЕТ КОГДА ПРИШЁЛ НЕ ВАЛИДНЫЙ КЛЮЧ,
            //ЧТО ОЗНАЧАЕТ ОШУБКУ СО СТОРОНЫ РАЗРАБОТЧИКА
//            return State.WRONG_KEY;
            return false;
        } catch (SignatureException e) {
            e.printStackTrace();
//            return State.WRONG_SIGNATURE;
            return false;
        } catch (ExpiredJwtException e) {
//            return State.LIFE_TIME_OUT;
            return false;
        }
    }

    public static PrivateKey readPrivateKey() {

        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File file = new File(classLoader.getResource("key.pem").getFile());
            PEMParser pemParser = new PEMParser(new FileReader(file));

            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object object = pemParser.readObject();
            KeyPair kp = converter.getKeyPair((PEMKeyPair) object);
            return kp.getPrivate();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PublicKey readPublicKey() {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File file = new File(classLoader.getResource("public.pem").getFile());

            try (FileReader keyReader = new FileReader(file)) {
                PEMParser pemParser = new PEMParser(keyReader);
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
                SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
                return converter.getPublicKey(publicKeyInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
