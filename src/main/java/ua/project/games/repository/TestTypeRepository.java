package ua.project.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.project.games.entity.TestType;
import ua.project.games.entity.enums.CurrentStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestTypeRepository extends JpaRepository<TestType, Long> {
    Optional<TestType> findByTestType(String testType);
    List<TestType> findAllByCurrentStatus(CurrentStatus currentStatus);
}
