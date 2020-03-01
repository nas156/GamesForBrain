package ua.project.games;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GamesApplication {
    public static void main(String[] args) {
        SpringApplication.run(GamesApplication.class, args);
    }
}
