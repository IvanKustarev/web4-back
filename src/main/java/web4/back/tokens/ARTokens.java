package web4.back.tokens;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ARTokens {
    String accessToken;
    String refreshToken;

    public ARTokens(Token accessToken, Token refreshToken) {
        this.accessToken = accessToken.toString();
        this.refreshToken = refreshToken.toString();
    }
}
