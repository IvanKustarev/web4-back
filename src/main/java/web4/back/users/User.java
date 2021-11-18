package web4.back.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String salt;
    private String refreshToken;
    private String accessToken;

    public User(String username, String salt) {
        this.username = username;
        this.salt = salt;
    }
}
