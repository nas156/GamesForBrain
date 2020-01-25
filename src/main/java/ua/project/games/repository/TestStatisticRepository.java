package ua.project.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.User;

import java.util.List;

public interface TestStatisticRepository extends JpaRepository<TestStatistic, Long> {
    List<TestStatistic> findAllByUser(User user);

}
