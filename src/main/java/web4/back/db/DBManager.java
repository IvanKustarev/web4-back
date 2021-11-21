package web4.back.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web4.back.dots.Dot;
import web4.back.repositories.DotsRepository;
import web4.back.repositories.UsersRepository;
import web4.back.users.model.AuthType;
import web4.back.users.model.User;
import web4.back.tokens.Token;

import java.util.List;

@Component
public class DBManager implements DBManaging{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DotsRepository dotsRepository;

    @Override
    public void addUser(User user) {
        usersRepository.save(user);
    }

    @Override
    public void addPasswordForUser(long userId, String password) {
        User user = usersRepository.findById(userId).get();
        user.setPassword(password);
        usersRepository.save(user);
    }

    @Override
    public void addDot(Dot dot) {
        dotsRepository.save(dot);
    }

    @Override
    public List<Dot> getUserDots(long userId) {
        return dotsRepository.findAllByUserId(userId);
    }

    @Override
    public User findUserById(long userId) {
        return usersRepository.findById(userId).get();
    }

    @Override
    public void addAccessToken(long userId, Token token) {
        User user = usersRepository.findById(userId).get();
        user.setAccessToken(token.toString());
        usersRepository.save(user);
    }

    @Override
    public void addRefreshToken(long userId, Token token) {
        User user = usersRepository.findById(userId).get();
        user.setRefreshToken(token.toString());
        usersRepository.save(user);
    }

    @Override
    public User findUserByAccess(Token accessToken) {
        return usersRepository.findByAccessToken(accessToken.toString());
    }

    @Override
    public User findUserByNameAndAuthType(String name, AuthType authType) {
        return usersRepository.findByUsernameAndAuthType(name, authType);
    }
}
