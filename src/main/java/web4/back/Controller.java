package web4.back;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserManaging userManaging;
    @Autowired
    private TokensManaging tokensManaging;
    @Autowired
    private DotManaging dotManaging;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @CrossOrigin
    @PostMapping("/generateTokenAndSalt")
    private TokenAndSalt generateTokenAndSalt(@RequestParam("username") String username) {
        return userManaging.generateTokenAndSalt(username);
    }

    @CrossOrigin
    @PutMapping("/password")
    private void putPassword(@RequestParam("token") Token token, @RequestParam("password") String password) {
        userManaging.putPassword(token, password);
    }

    @CrossOrigin
    @PostMapping("/checkUser")
    private boolean checkUser(@RequestParam("username") String username) {
        return userManaging.checkUser(username);
    }

    @CrossOrigin
    @PostMapping("/getTokenAndSalt")
    private TokenAndSalt getTokenAndSalt(@RequestParam("username") String username) {
        return userManaging.getTokenAndSalt(username);
    }

    @CrossOrigin
    @PostMapping("/password")
    private AuthResponse postPassword(@RequestParam("token") Token token, @RequestParam("password") String password) {
        return userManaging.postPassword(token, password);
    }

    @CrossOrigin
    @PostMapping("/getMyDots")
    private List<Dot> getMyDots(@RequestParam("accessToken") String accessToken) {
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
        return tokensManaging.updateTokens(new Token(refreshToken));
    }

    @CrossOrigin
    @PostMapping("/checkToken")
    private boolean checkToken(@RequestParam("token") String token) {
        return tokensManaging.check(new Token(token));
    }

    @CrossOrigin
    @PutMapping("/addDot")
    private void addDot(@RequestParam("token") String accessToken, @RequestParam("x") Double x, @RequestParam("y") Double y, @RequestParam("r") Double r) {
        dotManaging.addDot(new Token(accessToken), new Dot(x, y, r));
        String userIdentify = tokensManaging.findUserIdentify(new Token(accessToken));
        messagingTemplate.convertAndSendToUser(String.valueOf(userIdentify), "/queue/messages", "Win");
    }

    @CrossOrigin
    @PostMapping("/signByVk")
    private AuthResponse signByVk(@RequestParam("mid") String vkId, @RequestParam("parameters") String parametersForHash, @RequestParam("sig") String sig) {
        return userManaging.signInByVk(vkId, parametersForHash, sig);
    }

    @CrossOrigin
    @PostMapping("/signByGoogle")
    private AuthResponse signByGoogle(@RequestParam("idTokenString") String idTokenString) {
        return userManaging.signInByGoogle(idTokenString);
    }
}
