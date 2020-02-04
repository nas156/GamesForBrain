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
        if (notAnonymousCheck(testStatisticDTO.getUsername())){
            TestStatistic testStatisticEntity = TestStatistic.builder()
                    .score(testStatisticDTO.getScore())
                    .testDate(LocalDate.now())
                    .testType(TestType.valueOf(testStatisticDTO.getTestType()))
                    .user(userService.loadUserByUsername(testStatisticDTO.getUsername()))
                    .build();
            testStatisticRepository.save(testStatisticEntity);
        }
        log.warn("IN anonymous user posted statistic");
    }

    public List<TestStatistic> getAll() {
        return testStatisticRepository.findAll();
    }

    private boolean notAnonymousCheck(String username){
        return !username.equals("anonymousUser");
    }

    public List<TestStatistic> getUserStatisticForParticularTest(TestType testType, String username){
        return testStatisticRepository.findAllByTestTypeAndUser_Username(testType, username).get();
    }

    public List<TestStatistic> getStatisticForTest(TestType testType){
        return testStatisticRepository.findAllByTestType(testType).get();
    }
}

