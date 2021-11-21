package web4.back.repositories;

import org.springframework.data.repository.CrudRepository;
import web4.back.users.model.AuthType;
import web4.back.users.model.User;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByAccessToken(String accessToken);
    User findByUsernameAndAuthType(String username, AuthType authType);
}
