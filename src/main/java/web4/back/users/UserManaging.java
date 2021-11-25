package web4.back.users;

import web4.back.AuthResponse;
import web4.back.tokens.Token;
import web4.back.tokens.TokenAndSalt;

public interface UserManaging {
    TokenAndSalt generateTokenAndSalt(String username);
    boolean putPassword(Token token, String password);
    boolean checkUser(String userName);
    TokenAndSalt getTokenAndSalt(String username);
    AuthResponse postPassword(Token token, String password);
    AuthResponse signInByVk(String vkId, String parametersForHash, String sig);
    AuthResponse signInByGoogle(String idTokenString);
}
