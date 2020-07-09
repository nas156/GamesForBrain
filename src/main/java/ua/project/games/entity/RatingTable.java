package ua.project.games.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class RatingTable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private TestStatistic testStatistic;

    public RatingTable(TestStatistic testStatistic) {
        this.testStatistic = testStatistic;
    }

    public int getScore(){
        return this.testStatistic.getScore();
    }
}
