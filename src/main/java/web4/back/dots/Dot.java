package web4.back.dots;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "DOTS")
public class Dot {
    @Id
    @GeneratedValue
    private Long id;
    private Integer x;
    private Integer y;
    private Boolean get;
    private Long userId;
}
