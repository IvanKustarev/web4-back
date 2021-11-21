package web4.back.users.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
    @Column(length = 500)
    private String refreshToken;
    @Column(length = 500)
    private String accessToken;
    private AuthType authType;

    public User(String username, String salt, AuthType authType) {
        this.username = username;
        this.salt = salt;
        this.authType = authType;
    }
}
