package ua.project.games.dto;

import lombok.*;
import ua.project.games.entity.enums.TestType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestStatisticDTO {
    int score;
    String username;
    String testType;
    String testDate;
}
