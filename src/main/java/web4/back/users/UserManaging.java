package web4.back.users;

import web4.back.tokens.ARTokens;
import web4.back.tokens.Token;
import web4.back.tokens.TokenAndSalt;

public interface UserManaging {
    TokenAndSalt generateTokenAndSalt(String username);
    boolean putPassword(Token token, String password);
    boolean checkUser(String userName);
    TokenAndSalt getTokenAndSalt(String username);
    ARTokens postPassword(Token token, String password);
    ARTokens signInByVk(String vkId, String parametersForHash, String sig);
    ARTokens signInByGoogle(String idTokenString);
}
