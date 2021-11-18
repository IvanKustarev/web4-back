package web4.back.tokens;

import web4.back.tokens.Token;

public class TokenAndSalt {
    private Token token;
    private String salt;

    public TokenAndSalt(Token token, String salt) {
        this.token = token;
        this.salt = salt;
    }
}
