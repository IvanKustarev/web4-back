package web4.back.tokens;

public class ARTokens {
    String accessToken;
    String refreshToken;

    public ARTokens(Token accessToken, Token refreshToken) {
        this.accessToken = accessToken.toString();
        this.refreshToken = refreshToken.toString();
    }
}
