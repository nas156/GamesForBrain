package ua.project.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.games.entity.TestStatistic;


import java.util.List;
import java.util.Optional;

@Repository
public interface TestStatisticRepository extends JpaRepository<TestStatistic, Long> {
    Optional<List<TestStatistic>> findAllByUser_Username(String username);
    Optional<List<TestStatistic>> findAllByTestType_TestTypeAndUser_Username(String testType, String username);
    Optional<List<TestStatistic>> findTop100ByTestType_TestTypeOrderByIdDesc(String testType);
    Optional<List<TestStatistic>> findTop100ByTestType_TestTypeAndUser_UsernameOrderByScoreDesc(String testType, String username);
    Optional<List<TestStatistic>> findTopByScoreGreaterThanAndTestType_TestTypeOrderByScore(int score, String testType_testType);
}
