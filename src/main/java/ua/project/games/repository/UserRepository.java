package ua.project.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.project.games.anotations.AdminRepo;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.User;

import java.util.Optional;

@AdminRepo(name = User.class)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}