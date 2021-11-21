package web4.back.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web4.back.db.DBManaging;
import web4.back.tokens.ARTokens;
import web4.back.tokens.Token;
import web4.back.tokens.TokenAndSalt;
import web4.back.tokens.TokensManaging;
import web4.back.users.model.AuthType;
import web4.back.users.model.User;

import java.util.Random;

@Component
public class UsersManager implements UserManaging {

    @Autowired
    private TokensManaging tokensManaging;
    @Autowired
    private DBManaging dbManaging;
    @Autowired
    private SignInValidators signInValidators;

    @Override
    public TokenAndSalt generateTokenAndSalt(String username) {
        Token token = tokensManaging.generateSmall(username);
        String salt = generateSalt();
        User user = new User(username, salt, AuthType.USUAL);
        user.setAccessToken(token.toString());
        dbManaging.addUser(user);
        return new TokenAndSalt(token, salt);
    }

    @Override
    public boolean putPassword(Token token, String password) {
        if (tokensManaging.check(token)) {
            String username = tokensManaging.findUserIdentify(token);
            User user = dbManaging.findUserByNameAndAuthType(username, AuthType.USUAL);
            dbManaging.addPasswordForUser(user.getId(), password);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkUser(String username) {
        User user = dbManaging.findUserByNameAndAuthType(username, AuthType.USUAL);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TokenAndSalt getTokenAndSalt(String username) {
        User user = dbManaging.findUserByNameAndAuthType(username, AuthType.USUAL);
        if (user != null) {
            return new TokenAndSalt(tokensManaging.generateSmall(String.valueOf(user.getId())), user.getSalt());
        }
        return null;
    }

    @Override
    public ARTokens postPassword(Token token, String password) {
        if (tokensManaging.check(token)) {
            String userId = tokensManaging.findUserIdentify(token);
            User user = dbManaging.findUserById(Long.valueOf(userId));
            if (user.getPassword().equals(password)) {
                Token accessToken = tokensManaging.generateAccess(String.valueOf(user.getId()));
                Token refreshToken = tokensManaging.generateRefresh(String.valueOf(user.getId()));
                dbManaging.addAccessToken(user.getId(), accessToken);
                dbManaging.addRefreshToken(user.getId(), refreshToken);
                return new ARTokens(accessToken, refreshToken);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public ARTokens signInByVk(String vkId, String parametersForHash, String sig) {
        String userId = signInValidators.getValidVkUserId(vkId, parametersForHash, sig);
        return addSignedInUser(userId, AuthType.VK);
    }

    @Override
    public ARTokens signInByGoogle(String idTokenString) {
        String userId = signInValidators.getValidGoogleUserId(idTokenString);
        return addSignedInUser(userId, AuthType.GOOGLE);
    }

    /**
     * For google and vk auth
     * @param userName
     * @param authType
     * @return
     */
    private ARTokens addSignedInUser(String userName, AuthType authType){
        if (userName == null) {
            return null;
        } else {
            User user = dbManaging.findUserByNameAndAuthType(userName, authType);
            if (user == null) {
                user = new User(userName, null, authType);
                dbManaging.addUser(user);
                user = dbManaging.findUserByNameAndAuthType(userName, authType);
            }
            Token accessToken = tokensManaging.generateAccess(String.valueOf(user.getId()));
            Token refreshToken = tokensManaging.generateRefresh(String.valueOf(user.getId()));
            dbManaging.addAccessToken(user.getId(), accessToken);
            dbManaging.addRefreshToken(user.getId(), refreshToken);
            return new ARTokens(accessToken, refreshToken);
        }
    }

    private String generateSalt() {
        int length = 10;
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
