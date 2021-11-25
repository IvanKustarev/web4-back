package web4.back.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web4.back.repositories.UsersRepository;
import web4.back.tokens.Token;
import web4.back.users.model.AuthType;
import web4.back.users.model.User;

@Service
public class UserDBService  {
    @Autowired
    private UsersRepository usersRepository;

    public void addUser(User user) {
        usersRepository.save(user);
    }

    public void addPasswordForUser(long userId, String password) {
        User user = usersRepository.findById(userId).get();
        user.setPassword(password);
        usersRepository.save(user);
    }

    public User findUserById(long userId) {
        return usersRepository.findById(userId).get();
    }

    public void addAccessToken(long userId, Token token) {
        User user = usersRepository.findById(userId).get();
        user.setAccessToken(token.toString());
        usersRepository.save(user);
    }

    public void addRefreshToken(long userId, Token token) {
        User user = usersRepository.findById(userId).get();
        user.setRefreshToken(token.toString());
        usersRepository.save(user);
    }

    public User findUserByAccess(Token accessToken) {
        return usersRepository.findByAccessToken(accessToken.toString());
    }

    public User findUserByNameAndAuthType(String name, AuthType authType) {
        return usersRepository.findByUsernameAndAuthType(name, authType);
    }
}

//@Component
//public class DBManager implements DBManaging{
//
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Autowired
//    private DotsRepository dotsRepository;
//
//    @Override
//    public void addUser(User user) {
//        usersRepository.save(user);
//    }
//
//    @Override
//    public void addPasswordForUser(long userId, String password) {
//        User user = usersRepository.findById(userId).get();
//        user.setPassword(password);
//        usersRepository.save(user);
//    }
//
//    @Override
//    public void addDot(Dot dot) {
//        dotsRepository.save(dot);
//    }
//
//    @Override
//    public List<Dot> getUserDots(long userId) {
//        return dotsRepository.findAllByUserId(userId);
//    }
//
//    @Override
//    public User findUserById(long userId) {
//        return usersRepository.findById(userId).get();
//    }
//
//    @Override
//    public void addAccessToken(long userId, Token token) {
//        User user = usersRepository.findById(userId).get();
//        user.setAccessToken(token.toString());
//        usersRepository.save(user);
//    }
//
//    @Override
//    public void addRefreshToken(long userId, Token token) {
//        User user = usersRepository.findById(userId).get();
//        user.setRefreshToken(token.toString());
//        usersRepository.save(user);
//    }
//
//    @Override
//    public User findUserByAccess(Token accessToken) {
//        return usersRepository.findByAccessToken(accessToken.toString());
//    }
//
//    @Override
//    public User findUserByNameAndAuthType(String name, AuthType authType) {
//        return usersRepository.findByUsernameAndAuthType(name, authType);
//    }
//}
