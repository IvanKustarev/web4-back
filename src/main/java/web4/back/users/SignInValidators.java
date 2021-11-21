package web4.back.users;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Collections;

@Component
public class SignInValidators {

    public String getValidGoogleUserId(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList("326055707950-1lbfi3leui4j86b50f1dqcnmnupatip9.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                String userId = payload.getSubject();
                return userId;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String getValidVkUserId(String vkId, String parametersForHash, String sig){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(parametersForHash.concat("B1WDxFHyNAkjLfziCPXp").getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter
                    .printHexBinary(digest);
            if (myHash.equals(sig.toUpperCase())) {
                return vkId;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
