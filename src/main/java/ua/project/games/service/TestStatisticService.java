package ua.project.games.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.TestType;
import ua.project.games.entity.User;
import ua.project.games.entity.enums.CurrentStatus;
import ua.project.games.repository.TestStatisticRepository;
import ua.project.games.repository.TestTypeRepository;

import javax.swing.text.html.parser.Entity;
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

    /**
     * Метод, який знаходить всі записи з певним юзернеймом в таблиці TestStatistic
     * Якщо таких записів немає, повертається пустий масив
     * @param username юзернейм за яким відбувається пошук записів
     * @return List<TestStatistic> список об'єктів типу TestStatistic, які були знайдені в таблиці
     * @see TestStatistic
     * @see List
     * @see String
     */
    @Deprecated
    public List<TestStatistic> findAllTestsByUsername(String username) {
        return testStatisticRepository.findAllByUser_Username(username).orElse(new ArrayList<>());
    }

    /**
     * Метод який записує об'єкт типу TestStatistic в таблицю test_statistic
     * @param testStatistic об'єкт для запису в базу
     * @see TestStatistic
     * @see TestStatisticRepository
     */
    public void saveStatistic(TestStatistic testStatistic) {
        testStatisticRepository.save(testStatistic);
    }

    /**
     * Метод який записує кожен об'єкт типу TestStatistic зі списку в таблицю test_statistic
     * @param testStatistics список об'єктів для запису в базу
     * @see TestStatistic
     * @see List
     * @see TestStatisticRepository
     */
    public void saveStatistic(List<TestStatistic> testStatistics) {
        testStatisticRepository.saveAll(testStatistics);
    }

    /**
     * Метод, який створює і записує в таблицю test_statistic об'єкт типу TestStatistic,
     * який збираэться з полів переданого об'єкта TestStatisticDTO
     * @param testStatisticDTO об'єкт з полів якого збирається TestStatistic
     * @see TestStatistic
     * @see TestStatisticDTO
     * @see TestStatisticRepository
     * @see Slf4j
     */
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

