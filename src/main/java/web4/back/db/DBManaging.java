package web4.back.db;

import web4.back.dots.Dot;
import web4.back.users.User;

import java.util.List;

public interface DBManaging {
    User findUserByName(String username);
    void addUser(String username);
    void addPasswordForUser(long userId, String password);
    void addDot(long userId, Dot dot);
    List<Dot> getUserDots(long userId);
}
