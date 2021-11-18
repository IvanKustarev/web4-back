package web4.back.dots;

import org.springframework.stereotype.Component;
import web4.back.users.tokens.Token;

import java.util.List;

@Component
public class DotManager implements DotManaging{
    @Override
    public void addDot(Token accessToken, Dot dot) {

    }

    @Override
    public List<Dot> getMyDots(Token accessToken) {
        return null;
    }
}
