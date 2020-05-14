package ua.project.games.entity;

import lombok.*;
import ua.project.games.annotations.AdminPage;
import ua.project.games.entity.enums.CurrentStatus;

import javax.persistence.*;
@AdminPage
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

    //todo useless field. rename all templates with tests to their testType name and change fields in test url column than delete this field
    @Column
    private String testType;

    //todo rename to templateName
    @Column
    private String TestURL;

    @Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;
}
