package ua.project.games.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.enums.TestType;
import ua.project.games.repository.TestStatisticRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TestStatisticService {
    private final TestStatisticRepository testStatisticRepository;
    private final UserService userService;


    public TestStatisticService(TestStatisticRepository testStatisticRepository, UserService userService) {
        this.testStatisticRepository = testStatisticRepository;
        this.userService = userService;
    }

    //TODO optional checking
    public List<TestStatistic> findAllTestsByUsername(String username) {
        return testStatisticRepository.findAllByUser_Username(username).get();
    }

    public void saveStatistic(TestStatistic testStatistic) {
        testStatisticRepository.save(testStatistic);
    }

    public void saveStatistic(List<TestStatistic> testStatistics) {
        testStatisticRepository.saveAll(testStatistics);
    }

    public void createStatistic(TestStatisticDTO testStatisticDTO) {
        TestStatistic testStatisticEntity = new TestStatistic();
        testStatisticEntity.setScore(testStatisticDTO.getScore());
        testStatisticEntity.setTestDate(LocalDate.now());
        //TODO make validator for this part
        if (!testStatisticDTO.getUsername().equals("anonymousUser")) {
            testStatisticEntity.setUser(userService.loadUserByUsername(testStatisticDTO.getUsername()));
        } else {
            log.warn("IN anonymous user posted statistic");
            return;
        }
        testStatisticEntity.setTestType(TestType.valueOf(testStatisticDTO.getTestType()));
        testStatisticRepository.save(testStatisticEntity);
    }

    public List<TestStatistic> getAll() {
        return testStatisticRepository.findAll();
    }
}
