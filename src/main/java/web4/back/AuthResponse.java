package web4.back;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web4.back.tokens.Token;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    long userId;
    String accessToken;
    String refreshToken;
    
    public AuthResponse(Token accessToken, Token refreshToken, long userId) {
        this.userId = userId;
        this.accessToken = accessToken.toString();
        this.refreshToken = refreshToken.toString();
    }
}
