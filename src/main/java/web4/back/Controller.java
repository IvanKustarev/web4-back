package web4.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web4.back.dots.Dot;
import web4.back.dots.DotManaging;
import web4.back.users.TokenAndSalt;
import web4.back.users.UserManaging;
import web4.back.users.tokens.*;

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
    private List<Dot> getMyDots(@RequestParam("accessToken") Token accessToken){
        return dotManaging.getMyDots(accessToken);
    }

    @CrossOrigin
    @PostMapping("/updateTokens")
    private ARTokens updateTokens(@RequestParam("refreshToken")Token refreshToken){
        return tokensManaging.updateTokens(refreshToken);
    }

    @CrossOrigin
    @PostMapping("/checkToken")
    private boolean checkToken(@RequestParam("token") Token token){
        return tokensManaging.checkToken(token);
    }

    @CrossOrigin
    @PutMapping("/addDot")
    private void addDot(@RequestParam("token") Token accessToken, @RequestParam("dot") Dot dot){
        dotManaging.addDot(accessToken, dot);
    }

}
