package ua.project.games.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.project.games.entity.TestStatistic;

import java.awt.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllTestsStatisticDTO {
    private List<Integer> repeatNumbersTestStatistic;
    private List<Integer> reactionTestStatistic;
    private List<Integer> repeatSequenceTestStatistic;
    private List<Integer> countGreenTestStatistic;
    private List<Integer> isPreviousTestStatistic;
}
