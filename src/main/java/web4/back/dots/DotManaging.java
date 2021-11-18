package web4.back.dots;

import web4.back.users.tokens.Token;

import java.util.List;

public interface DotManaging {
    void addDot(Token accessToken, Dot dot);
    List<Dot> getMyDots(Token accessToken);
}
