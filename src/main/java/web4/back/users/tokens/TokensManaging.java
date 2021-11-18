package web4.back.users.tokens;

public interface TokensManaging {
    ARTokens updateTokens(Token refreshToken);
    boolean checkToken(Token token);
    long getIdFromToken(Token accessToken);
}
