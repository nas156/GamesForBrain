package ua.project.games.entity;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import ua.project.games.entity.enums.Role;
import ua.project.games.entity.enums.TestType;

import javax.persistence.*;
import java.time.LocalDate;

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
    private User user;

    @Enumerated(EnumType.STRING)
    private TestType testType;


}
