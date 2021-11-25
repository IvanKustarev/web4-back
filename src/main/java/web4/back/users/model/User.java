package web4.back.users.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web4.back.dots.Dot;

import javax.persistence.*;
import java.util.List;

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
//    @OneToMany(targetEntity= User.class, mappedBy="userId",/*cascade=CascadeType.ALL, */fetch = FetchType.LAZY)
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Dot> dotList;

    public User(String username, String salt, AuthType authType) {
        this.username = username;
        this.salt = salt;
        this.authType = authType;
    }
}
