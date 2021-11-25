package web4.back.repositories;

import org.springframework.data.repository.CrudRepository;
import web4.back.dots.Dot;
import web4.back.users.model.User;

import java.util.List;

public interface DotsRepository extends CrudRepository<Dot, Long> {
    List<Dot> findAllByUser(User user);
}
