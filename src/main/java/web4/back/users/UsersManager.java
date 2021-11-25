package web4.back.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web4.back.AuthResponse;
import web4.back.db.DotDBService;
import web4.back.db.UserDBService;
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
    private DotDBService dotDBService;
    @Autowired
    private UserDBService userDBService;
    @Autowired
    private SignInValidators signInValidators;

    @Override
    public TokenAndSalt generateTokenAndSalt(String username) {
        Token token = tokensManaging.generateSmall(username);
        String salt = generateSalt();
        User user = new User(username, salt, AuthType.USUAL);
        user.setAccessToken(token.toString());
        userDBService.addUser(user);
        return new TokenAndSalt(token, salt);
    }

    @Override
    public boolean putPassword(Token token, String password) {
        if (tokensManaging.check(token)) {
            String username = tokensManaging.findUserIdentify(token);
            User user = userDBService.findUserByNameAndAuthType(username, AuthType.USUAL);
            userDBService.addPasswordForUser(user.getId(), password);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkUser(String username) {
        User user = userDBService.findUserByNameAndAuthType(username, AuthType.USUAL);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TokenAndSalt getTokenAndSalt(String username) {
        User user = userDBService.findUserByNameAndAuthType(username, AuthType.USUAL);
        if (user != null) {
            return new TokenAndSalt(tokensManaging.generateSmall(String.valueOf(user.getId())), user.getSalt());
        }
        return null;
    }

    @Override
    public AuthResponse postPassword(Token token, String password) {
        if (tokensManaging.check(token)) {
            String userId = tokensManaging.findUserIdentify(token);
            User user = userDBService.findUserById(Long.valueOf(userId));
            if (user.getPassword().equals(password)) {
                Token accessToken = tokensManaging.generateAccess(String.valueOf(user.getId()));
                Token refreshToken = tokensManaging.generateRefresh(String.valueOf(user.getId()));
                userDBService.addAccessToken(user.getId(), accessToken);
                userDBService.addRefreshToken(user.getId(), refreshToken);
                return new AuthResponse(accessToken, refreshToken, user.getId());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public AuthResponse signInByVk(String vkId, String parametersForHash, String sig) {
        String userId = signInValidators.getValidVkUserId(vkId, parametersForHash, sig);
        return addSignedInUser(userId, AuthType.VK);
    }

    @Override
    public AuthResponse signInByGoogle(String idTokenString) {
        String userId = signInValidators.getValidGoogleUserId(idTokenString);
        return addSignedInUser(userId, AuthType.GOOGLE);
    }

    /**
     * For google and vk auth
     * @param userName
     * @param authType
     * @return
     */
    private AuthResponse addSignedInUser(String userName, AuthType authType){
        if (userName == null) {
            return null;
        } else {
            User user = userDBService.findUserByNameAndAuthType(userName, authType);
            if (user == null) {
                user = new User(userName, null, authType);
                userDBService.addUser(user);
                user = userDBService.findUserByNameAndAuthType(userName, authType);
            }
            Token accessToken = tokensManaging.generateAccess(String.valueOf(user.getId()));
            Token refreshToken = tokensManaging.generateRefresh(String.valueOf(user.getId()));
            userDBService.addAccessToken(user.getId(), accessToken);
            userDBService.addRefreshToken(user.getId(), refreshToken);
            return new AuthResponse(accessToken, refreshToken, user.getId());
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
