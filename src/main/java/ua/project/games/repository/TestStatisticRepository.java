package ua.project.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.User;
import ua.project.games.entity.enums.TestType;

import java.util.List;
import java.util.Optional;

public interface TestStatisticRepository extends JpaRepository<TestStatistic, Long> {
    Optional<List<TestStatistic>> findAllByUser_Username(String username);
    Optional<List<TestStatistic>> findAllByTestTypeAndUser_Username(TestType testType, String username);
    Optional<List<TestStatistic>> findTop100ByTestTypeOrderByIdDesc(TestType testType);
}
