package web4.back.repositories;

import org.springframework.data.repository.CrudRepository;
import web4.back.dots.Dot;

import java.util.List;

public interface DotsRepository extends CrudRepository<Dot, Long> {
    List<Dot> findAllByUserId(long userId);
}
