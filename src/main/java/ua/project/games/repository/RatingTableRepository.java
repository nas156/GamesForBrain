package ua.project.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.games.entity.RatingTable;

import java.util.List;
import java.util.Optional;

public interface RatingTableRepository extends JpaRepository<RatingTable, Long> {
    Optional<List<RatingTable>> findAllByTestStatistic_TestType_TestType(String testType);
}
