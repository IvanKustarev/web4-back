package web4.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web4.back.dots.Dot;
import web4.back.dots.DotManaging;
import web4.back.tokens.ARTokens;
import web4.back.tokens.Token;
import web4.back.tokens.TokenAndSalt;
import web4.back.tokens.TokensManaging;
import web4.back.users.UserManaging;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private UserManaging userManaging;
    @Autowired
    private TokensManaging tokensManaging;
    @Autowired
    private DotManaging dotManaging;

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
    private TokenAndSalt getTokenAndSalt(@RequestParam("username")String username){
        return userManaging.getTokenAndSalt(username);
    }

    @CrossOrigin
    @PostMapping("/password")
    private ARTokens postPassword(@RequestParam("token") Token token, @RequestParam("password") String password){
        return userManaging.postPassword(token, password);
    }

    @CrossOrigin
    @PostMapping("/getMyDots")
    private List<Dot> getMyDots(@RequestParam("accessToken") String accessToken){
        return dotManaging.getMyDots(new Token(accessToken));
    }

    @CrossOrigin
    @PostMapping("/updateTokens")
    private ARTokens updateTokens(@RequestParam("refreshToken")String refreshToken){
        return tokensManaging.updateTokens(new Token(refreshToken));
    }

    @CrossOrigin
    @PostMapping("/checkToken")
    private boolean checkToken(@RequestParam("token") String token){
        return tokensManaging.check(new Token(token));
    }

    @CrossOrigin
    @PutMapping("/addDot")
    private void addDot(@RequestParam("token") String accessToken, @RequestParam("x") Double x, @RequestParam("y") Double y){
        dotManaging.addDot(new Token(accessToken), new Dot(x, y));
    }

    @CrossOrigin
    @PostMapping("/signByVk")
    private ARTokens signByVk(@RequestParam("mid")String vkId, @RequestParam("parameters") String parametersForHash, @RequestParam("sig") String sig){
        return userManaging.signInByVk(vkId, parametersForHash, sig);
    }

    @CrossOrigin
    @PostMapping("/signByGoogle")
    private ARTokens signByGoogle(@RequestParam("idTokenString") String idTokenString){
        return userManaging.signInByGoogle(idTokenString);
    }
}
