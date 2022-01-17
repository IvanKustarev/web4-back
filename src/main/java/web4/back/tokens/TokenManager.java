package web4.back.tokens;

import io.jsonwebtoken.*;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web4.back.AuthResponse;
import web4.back.db.UserDBService;

import javax.annotation.PostConstruct;
import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Date;

@Component
public class TokenManager implements TokensManaging {

    private static final Logger log = Logger.getLogger(TokenManager.class);

    protected static PublicKey publicKey;
    protected static PrivateKey privateKey;

    @PostConstruct
    public void readKeysFromResources() throws IOException {
        publicKey = readPublicKey();
        privateKey = readPrivateKey();
    }

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
            if (userDBService.findUserById(new Long(userId)).getRefreshToken().equals(refreshToken.toString())) {
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
//        readPrivateKey();

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
            return true;
        } catch (MalformedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            //БОЛЬШЕ ДЛЯ ПРОВЕРКИ, ТК ВЫСКАКИВАЕТ КОГДА ПРИШЁЛ НЕ ВАЛИДНЫЙ КЛЮЧ,
            //ЧТО ОЗНАЧАЕТ ОШУБКУ СО СТОРОНЫ РАЗРАБОТЧИКА
            return false;
        } catch (SignatureException e) {
            e.printStackTrace();
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public PrivateKey readPrivateKey() throws IOException {

        final String keyFileName = "key.pem";
        try {
            log.info("Start read private key");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(keyFileName);
            if (inputStream == null) {
                log.error("File " + keyFileName + " not found");
                throw new FileNotFoundException("File " + keyFileName + " not found");
            } else {
                PEMParser pemParser = new PEMParser(new InputStreamReader(inputStream));
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
                Object object = pemParser.readObject();
                KeyPair kp = converter.getKeyPair((PEMKeyPair) object);
                PrivateKey privateKey = kp.getPrivate();
                log.info("Finish read private key");
                return privateKey;
            }
        } catch (IOException e) {
            log.error(e);
            throw e;
        }

    }

    public PublicKey readPublicKey() throws IOException {
        final String keyFileName = "public.pem";
        try {
            log.info("Start read public key");
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(keyFileName);
            if (inputStream == null) {
                log.error("File " + keyFileName + " not found");
                throw new FileNotFoundException("File " + keyFileName + " not found");
            } else {
                Reader reader = new InputStreamReader(inputStream);
                PEMParser pemParser = new PEMParser(reader);
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
                SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
                PublicKey publicKey = converter.getPublicKey(publicKeyInfo);
                log.info("Finish read public key");
                return publicKey;
            }
        } catch (IOException e) {
            log.error(e);
            throw e;
        }
    }
}
