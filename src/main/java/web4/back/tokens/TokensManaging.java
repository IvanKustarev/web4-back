package web4.back.tokens;

public interface TokensManaging {
    ARTokens updateTokens(Token refreshToken);
    String findUserIdentify(Token token);
    boolean check(Token token);
    Token generateSmall(String userIdentify);
    Token generateAccess(String userIdentify);
    Token generateRefresh(String userIdentify);
}
