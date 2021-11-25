package web4.back.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web4.back.dots.Dot;
import web4.back.repositories.DotsRepository;
import web4.back.users.model.User;

import java.util.List;

@Service
public class DotDBService {
    @Autowired
    private DotsRepository dotsRepository;

    public void addDot(Dot dot) {
        dotsRepository.save(dot);
    }

    public List<Dot> getUserDots(User user) {
        return dotsRepository.findAllByUser(user);
    }
}
