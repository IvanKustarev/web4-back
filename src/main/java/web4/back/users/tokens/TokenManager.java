package web4.back.users.tokens;

import org.springframework.stereotype.Component;

@Component
public class TokenManager implements TokensManaging{
    @Override
    public ARTokens updateTokens(Token refreshToken) {
        return null;
    }

    @Override
    public boolean checkToken(Token token) {
        return false;
    }

    @Override
    public long getIdFromToken(Token accessToken) {
        return 0;
    }
}
