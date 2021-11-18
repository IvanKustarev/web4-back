package web4.back.users.tokens;

import io.jsonwebtoken.*;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Date;

public class TokenUtils {

    protected static PublicKey publicKey = readPublicKey();
    protected static PrivateKey privateKey = readPrivateKey();

    public static boolean check(Token token) {
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

    public static Token create(int id, long period) {

        readPrivateKey();

        JwtBuilder builder = Jwts.builder();
        builder.claim("userId", id);
        Date date = new Date();
        builder.setIssuedAt(date);
        builder.setExpiration(new Date(date.getTime() + period));
        builder.signWith(SignatureAlgorithm.HS256, privateKey);
        return new Token(builder.compact());
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
