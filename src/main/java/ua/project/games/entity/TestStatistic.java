package ua.project.games.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import ua.project.games.entity.enums.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TestStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private int score;

    @Column
    private LocalDate testDate;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    private TestType testType;

}
