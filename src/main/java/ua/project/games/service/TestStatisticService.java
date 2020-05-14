package ua.project.games.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.TestType;
import ua.project.games.entity.User;
import ua.project.games.entity.enums.CurrentStatus;
import ua.project.games.repository.TestStatisticRepository;
import ua.project.games.repository.TestTypeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервісний клас для опису логіки роботи з даними статистики тестів
 * @see Service
 */
@Service
@Slf4j
public class TestStatisticService {
    private final TestStatisticRepository testStatisticRepository;
    private final UserService userService;
    private final TestTypeRepository testTypeRepository;

    /**
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param testStatisticRepository об'єкт репозиторію TestStatisticRepository, який містить методи для роботи з таблицею test_statistic
     * @param userService об'єкт сервісу userService, який містить логіку для роботи з класом User
     * @param testTypeRepository об'єкт репозиторію TestTypeRepository, який містить методи для роботи з таблицею test_type
     * @see TestStatisticRepository
     * @see UserService
     * @see TestTypeRepository
     * @see Autowired
     */
    @Autowired
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

    /**
     * Метод який дістає всі записи в таблиці test_statistic
     * @return список об'єктів класу TestStatistic
     * @see TestStatistic
     * @see TestTypeRepository
     */
    public List<TestStatistic> getAll() {
        return testStatisticRepository.findAll();
    }

    /**
     * Метод який приймеє на всіх об'єкти типу String та порівнює його з  строкою "anonymousUser"
     * @param username ім'я юзера, яке потрібно перевірити
     * @return true, якщо параметр не співпадає з строкою "anonymousUser", та false, якщо співпадає
     * @see String/equals()
     */
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
        return getAllActiveTests().stream().map(TestType::getTestType)
                .collect(Collectors.toMap(x -> x, x -> getUserScoreForParticularTest(x, username)));
    }

    public List<TestType> getAllActiveTests(){
        return testTypeRepository.findAllByCurrentStatus(CurrentStatus.Active);
    }

    public List<TestStatistic> getTopScoresForATest(String test, int score){
        return testStatisticRepository
                .findAllByScoreGreaterThanAndTestType_TestType(score, test)
                .orElse(new ArrayList<>());
    }

    public void deleteAllByUser(User user) {
        List<TestStatistic> userTests = user.getTests();
        testStatisticRepository.deleteAll(userTests);
    }
}

