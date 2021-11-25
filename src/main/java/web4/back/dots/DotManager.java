package web4.back.dots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web4.back.AreaChecker;
import web4.back.db.DBManaging;
import web4.back.tokens.Token;
import web4.back.users.model.User;

import java.util.List;

@Component
public class DotManager implements DotManaging{
    @Autowired
    private AreaChecker areaChecker;

    @Autowired
    private DBManaging dbManaging;

    @Override
    public void addDot(Token accessToken, Dot dot) {
        User user = dbManaging.findUserByAccess(accessToken);
        dot = checkDot(dot);
        dot.setUserId(user.getId());
        dbManaging.addDot(dot);
    }

    @Override
    public List<Dot> getMyDots(Token accessToken) {
        User user = dbManaging.findUserByAccess(accessToken);
        return dbManaging.getUserDots(user.getId());
    }

    private Dot checkDot(Dot dot){
        if(areaChecker.checkArea(dot)){
            dot.setGet(true);
        }else {
            dot.setGet(false);
        }
        return dot;
    }
}
