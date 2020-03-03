package ua.project.games.entity;

import lombok.*;
import ua.project.games.entity.enums.CurrentStatus;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TestType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String testType;

    //todo rewrite with small letter
    @Column
    private String TestURL;

    @Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;
}
