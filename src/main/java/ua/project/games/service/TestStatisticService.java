package ua.project.games.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.TestType;
import ua.project.games.entity.enums.CurrentStatus;
import ua.project.games.repository.TestStatisticRepository;
import ua.project.games.repository.TestTypeRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestStatisticService {
    private final TestStatisticRepository testStatisticRepository;
    private final UserService userService;
    private final TestTypeRepository testTypeRepository;


    public TestStatisticService(TestStatisticRepository testStatisticRepository, UserService userService, TestTypeRepository testTypeRepository) {
        this.testStatisticRepository = testStatisticRepository;
        this.userService = userService;
        this.testTypeRepository = testTypeRepository;
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
                    .testType(testTypeRepository.findByTestType(testStatisticDTO.getTestType()).get())
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


    public List<Integer> getStatisticForTest(String testType) {
        List<TestStatistic> testStatistics = testStatisticRepository
                .findTop100ByTestType_TestTypeOrderByIdDesc(testType)
                .orElse(new ArrayList<>());
        return testStatistics.stream().map(TestStatistic::getScore).collect(Collectors.toList());
    }

    public List<Integer> getUserScoreForParticularTest(String  testType, String username) {
        List<TestStatistic> testStatistics = testStatisticRepository
                .findTop100ByTestType_TestTypeAndUser_UsernameOrderByScoreDesc(testType, username)
                .orElse(new ArrayList<>());
        return testStatistics.stream().map(TestStatistic::getScore).collect(Collectors.toList());
    }

    public Map<String, List<Integer>> getAllTestsStatisticByUser(String username){
        Map<String, List<Integer>> allTestsStatistic = new HashMap<>();

        for (TestType testType: testTypeRepository.findAllByCurrentStatus(CurrentStatus.Active)
             ) {
            allTestsStatistic.put(testType.getTestType(), getUserScoreForParticularTest(testType.getTestType(), username));
        }

        return allTestsStatistic;
    }
}

