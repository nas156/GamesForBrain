package ua.project.games.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.enums.TestType;
import ua.project.games.repository.TestStatisticRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Deprecated
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
        if (notAnonymousCheck(testStatisticDTO.getUsername())) {
            TestStatistic testStatisticEntity = TestStatistic.builder()
                    .score(testStatisticDTO.getScore())
                    .testDate(LocalDate.now())
                    .testType(TestType.valueOf(testStatisticDTO.getTestType()))
                    .user(userService.loadUserByUsername(testStatisticDTO.getUsername()))
                    .build();
            saveStatistic(testStatisticEntity);
        } else {
            log.warn("IN anonymous user posted statistic");
        }
    }

    public List<TestStatistic> getAll() {
        return testStatisticRepository.findAll();
    }

    private boolean notAnonymousCheck(String username) {
        return !username.equals("anonymousUser");
    }


    public List<Integer> getStatisticForTest(TestType testType) {
        List<TestStatistic> testStatistics = testStatisticRepository
                .findTop100ByTestTypeOrderByIdDesc(testType)
                .orElse(new ArrayList<>());
        return testStatistics.stream().map(TestStatistic::getScore).collect(Collectors.toList());
    }

    public List<Integer> getUserScoreForParticularTest(TestType testType, String username) {
        List<TestStatistic> testStatistics = testStatisticRepository
                .findAllByTestTypeAndUser_Username(testType, username)
                .orElse(new ArrayList<>());
        return testStatistics.stream().map(TestStatistic::getScore).collect(Collectors.toList());
    }

}

