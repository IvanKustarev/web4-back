package web4.back.db;

import web4.back.dots.Dot;
import web4.back.users.User;

import java.util.List;

public class DBManager implements DBManaging{
    @Override
    public User findUserByName(String username) {
        return null;
    }

    @Override
    public void addUser(String username) {

    }

    @Override
    public void addPasswordForUser(long userId, String password) {

    }

    @Override
    public void addDot(long userId, Dot dot) {

    }

    @Override
    public List<Dot> getUserDots(long userId) {
        return null;
    }
}
