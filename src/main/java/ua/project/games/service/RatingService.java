package ua.project.games.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.project.games.dto.UsernameScoreDTO;
import ua.project.games.entity.RatingTable;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.TestType;
import ua.project.games.repository.RatingTableRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Сервісний клас, де описана логіка для роботи з рейтинговою системою
 * @see Service
 */
@Service
public class RatingService {
    private final int MINIMUM_SCORE_FOR_RATING_TABLE = 40;
    private final TestStatisticService testStatisticService;
    private final RatingTableRepository ratingTableRepository;

    /**
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param testStatisticService обє'єкт класу TestStatisticService, який містить логіку для роботи з класом TestStatistic
     * @param ratingTableRepository обєкт репозиторію RatingTableRepository, який містить методи для роботи з таблицею rating_table
     * @see TestStatisticService
     * @see RatingTableRepository
     * @see Autowired
     */
    @Autowired
    public RatingService(TestStatisticService testStatisticService, RatingTableRepository ratingTableRepository) {
        this.testStatisticService = testStatisticService;
        this.ratingTableRepository = ratingTableRepository;
    }

    /**
     * Метод, який збирає і повертає топ найкращих результатів статистики для всіх тестів
     * @return List<TestStatistic> список всієї статистики для всіх ігор
     * @see List
     * @see TestStatistic
     * @see java.util.stream.Stream
     */
    private List<TestStatistic> getAllTestStatisticForRatingTable(){
        return testStatisticService.getAllActiveTests().stream().map(TestType::getTestType)
                .flatMap(x -> testStatisticService.getTopScoresForATest(x, MINIMUM_SCORE_FOR_RATING_TABLE).stream())
                .collect(Collectors.toList());
    }

    /**
     * Метод, який створює список полів для рейтингової таблиці з списку статистики який повертає метод getAllTestStatisticForRatingTable()
     * @return список RatingTable об'єктів
     */
    private List<RatingTable> getAllRatingFields(){
       return getAllTestStatisticForRatingTable().stream()
               .map(RatingTable::new)
               .collect(Collectors.toList());
    }

    /**
     * Метод, який кожного дня о 18:00 оновлює рейтингову таблицю. Записує в неї резульат методу getAllRatingFields()
     * @see Transactional
     * @see RatingTableRepository
     * @see Scheduled
     */
    @Transactional
    @Scheduled(cron = "0 0 6 * * *")
    public void updateRatingTable(){
        ratingTableRepository.deleteAll();
        ratingTableRepository.saveAll(getAllRatingFields());
    }

    /**
     * Метод для пошуку та статистики тестів для певного тестового типу, які увійшли в рейтингову таблицю
     * @param testType ім'я тесту, для якого потрібно знайти статистику, яка знаходиться в рейтинговій таблиці
     * @return список об'єктів UsernameScoreDTO, для тестової статистики, яка увійшла в рейтингову таблицю rating_table
     * @see RatingTableRepository
     * @see java.util.stream.Stream
     * @see TestStatistic
     * @see UsernameScoreDTO
     */
    public List<UsernameScoreDTO> getParticularGameStatisticStatisticForRatingTable(String testType){
        return ratingTableRepository.findAllByTestStatistic_TestType_TestType(testType).orElse(new ArrayList<>()).stream()
                .sorted(Comparator.comparing(RatingTable::getScore).reversed())
                .filter(isDuplicateByAProperty(x -> x.getTestStatistic().getUser().getUsername()))
                .map(x -> new UsernameScoreDTO(x.getTestStatistic().getUser().getUsername(), x.getTestStatistic().getScore()))
                .collect(Collectors.toList());
    }

    /**
     * Метод для пошуку та статистики тестів для кожного типу тестів, які увійшли в рейтингову таблицю
     * @return Map<String, List<UsernameScoreDTO>> список результів методу getParticularGameStatisticStatisticForRatingTable() для кожного активного тесту
     * @see java.util.stream.Stream
     * @see UsernameScoreDTO
     * @see TestStatisticService
     */
    public Map<String, List<UsernameScoreDTO>> getRatingStatisticForEachTest(){
        return testStatisticService.getAllActiveTests().stream()
                .map(TestType::getTestType)
                .collect(Collectors.toMap(x -> x, this::getParticularGameStatisticStatisticForRatingTable));
    }

    private static <T> Predicate<T> isDuplicateByAProperty(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
