package web4.back.users;

import web4.back.users.tokens.ARTokens;
import web4.back.users.tokens.Token;

public interface UserManaging {
    TokenAndSalt generateTokenAndSalt(String username);
    ARTokens putPassword(Token token, String password);
    boolean checkUser(String userName);
    TokenAndSalt getTokenAndSalt(String username);
    ARTokens postPassword(Token token, String password);
}
