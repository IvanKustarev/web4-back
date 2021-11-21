package web4.back.tokens;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web4.back.tokens.Token;

@Getter
@Setter
@NoArgsConstructor
public class TokenAndSalt {
    private String token;
    private String salt;

    public TokenAndSalt(Token token, String salt) {
        this.token = token.toString();
        this.salt = salt;
    }
}
