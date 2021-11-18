package web4.back.repositories;

import org.springframework.data.repository.CrudRepository;
import web4.back.users.User;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByAccessToken(String accessToken);
}
