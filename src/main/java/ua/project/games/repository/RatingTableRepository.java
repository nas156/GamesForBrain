package ua.project.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.games.entity.RatingTable;

public interface RatingTableRepository extends JpaRepository<RatingTable, Long> {
}
