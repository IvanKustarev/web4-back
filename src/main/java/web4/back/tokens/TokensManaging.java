package web4.back.tokens;

import web4.back.AuthResponse;

public interface TokensManaging {
    AuthResponse updateTokens(Token refreshToken);
    String findUserIdentify(Token token);
    boolean check(Token token);
    Token generateSmall(String userIdentify);
    Token generateAccess(String userIdentify);
    Token generateRefresh(String userIdentify);

}
