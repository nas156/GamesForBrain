package ua.project.games.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestStatisticDTO {
    int score;
    String username;
    String testType;
}
