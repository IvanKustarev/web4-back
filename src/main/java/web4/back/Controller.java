package web4.back;

import org.apache.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import web4.back.dots.Dot;
import web4.back.dots.DotManaging;
import web4.back.tokens.Token;
import web4.back.tokens.TokenAndSalt;
import web4.back.tokens.TokensManaging;
import web4.back.users.UserManaging;
import web4.back.users.model.User;

import java.util.List;

@RestController
public class Controller {

    private static final Logger log = Logger.getLogger(Controller.class);

    private final UserManaging userManaging;
    private final TokensManaging tokensManaging;
    private final DotManaging dotManaging;
    private final SimpMessagingTemplate messagingTemplate;

    public Controller(UserManaging userManaging, TokensManaging tokensManaging, DotManaging dotManaging, SimpMessagingTemplate messagingTemplate) {
        this.userManaging = userManaging;
        this.tokensManaging = tokensManaging;
        this.dotManaging = dotManaging;
        this.messagingTemplate = messagingTemplate;
    }

    @CrossOrigin
    @PostMapping("/generateTokenAndSalt")
    private TokenAndSalt generateTokenAndSalt(@RequestParam("username") String username) {
        log.info("Post to /generateTokenAndSalt");
        return userManaging.generateTokenAndSalt(username);
    }

    @CrossOrigin
    @PutMapping("/password")
    private void putPassword(@RequestParam("token") Token token, @RequestParam("password") String password) {
        log.info("Put to /password");
        userManaging.putPassword(token, password);
    }

    @CrossOrigin
    @PostMapping("/checkUser")
    private boolean checkUser(@RequestParam("username") String username) {
        log.info("Post to /checkUser");
        return userManaging.checkUser(username);
    }

    @CrossOrigin
    @PostMapping("/getTokenAndSalt")
    private TokenAndSalt getTokenAndSalt(@RequestParam("username") String username) {
        log.info("Post to /getTokenAndSalt");
        return userManaging.getTokenAndSalt(username);
    }

    @CrossOrigin
    @PostMapping("/password")
    private AuthResponse postPassword(@RequestParam("token") Token token, @RequestParam("password") String password) {
        log.info("Post to /password");
        return userManaging.postPassword(token, password);
    }

    @CrossOrigin
    @PostMapping("/getMyDots")
    private List<Dot> getMyDots(@RequestParam("accessToken") String accessToken) {
        log.info("Post to /getMyDots");
        List<Dot> dots = dotManaging.getMyDots(new Token(accessToken));
        /*
        for - необходимо заполнить поля пользователей заглушками,
        а не то будет фозникать StackOverflow при попытке загрузки
        точек с пользователями, у каждого есть точки, у каждой точки
        есть пользователь и тд
         */
        for(Dot dot : dots){
            dot.setUser(new User());
        }
        return dots;
    }

    @CrossOrigin
    @PostMapping("/updateTokens")
    private AuthResponse updateTokens(@RequestParam("refreshToken") String refreshToken) {
        log.info("Post to /updateTokens");
        return tokensManaging.updateTokens(new Token(refreshToken));
    }

    @CrossOrigin
    @PostMapping("/checkToken")
    private boolean checkToken(@RequestParam("token") String token) {
        log.info("Post to /checkToken");
        return tokensManaging.check(new Token(token));
    }

    @CrossOrigin
    @PutMapping("/addDot")
    private void addDot(@RequestParam("token") String accessToken, @RequestParam("x") Double x, @RequestParam("y") Double y, @RequestParam("r") Double r) {
        log.info("Put to /addDot");
        dotManaging.addDot(new Token(accessToken), new Dot(x, y, r));
        String userIdentify = tokensManaging.findUserIdentify(new Token(accessToken));
        messagingTemplate.convertAndSendToUser(String.valueOf(userIdentify), "/queue/messages", "Win");
    }

    @CrossOrigin
    @PostMapping("/signByVk")
    private AuthResponse signByVk(@RequestParam("mid") String vkId, @RequestParam("parameters") String parametersForHash, @RequestParam("sig") String sig) {
        log.info("Post to /signByVk");
        return userManaging.signInByVk(vkId, parametersForHash, sig);
    }

    @CrossOrigin
    @PostMapping("/signByGoogle")
    private AuthResponse signByGoogle(@RequestParam("idTokenString") String idTokenString) {
        log.info("Post to /signByGoogle");
        return userManaging.signInByGoogle(idTokenString);
    }
}
