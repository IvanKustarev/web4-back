package web4.back.dots;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web4.back.users.model.User;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "DOTS")
public class Dot {
    @Id
    @GeneratedValue
    private Long id;
    private Double x;
    private Double y;
    private Double r;
    private Boolean get;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Dot(Double x, Double y, Double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
}
