package web4.back.db;

import web4.back.dots.Dot;
import web4.back.users.model.AuthType;
import web4.back.users.model.User;
import web4.back.tokens.Token;

import java.util.List;

public interface DBManaging {
    User findUserById(long userId);
    void addUser(User user);
    void addPasswordForUser(long userId, String password);
    void addDot(Dot dot);
    List<Dot> getUserDots(long userId);
    void addAccessToken(long userId, Token token);
    void addRefreshToken(long userId, Token token);
    User findUserByNameAndAuthType(String name, AuthType authType);
    User findUserByAccess(Token token);
}
