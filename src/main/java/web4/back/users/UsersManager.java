package web4.back.users;

import org.springframework.stereotype.Component;
import web4.back.users.tokens.ARTokens;
import web4.back.users.tokens.Token;

import java.util.Random;

@Component
public class UsersManager implements UserManaging {
    @Override
    public TokenAndSalt generateTokenAndSalt(String username) {
        return null;
    }

    @Override
    public ARTokens putPassword(Token token, String password) {
        return null;
    }

    @Override
    public boolean checkUser(String userName) {
        return false;
    }

    @Override
    public TokenAndSalt getTokenAndSalt(String username) {
        return null;
    }

    @Override
    public ARTokens postPassword(Token token, String password) {
        return null;
    }


    private String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
