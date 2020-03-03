package ua.project.games.service;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.project.games.dto.UsernameScoreDTO;
import ua.project.games.entity.RatingTable;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.TestType;
import ua.project.games.repository.RatingTableRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервісний клас, де описані методи для роботи з рейтинговою таблицею
 */
@Service
public class RatingService {
    private final int MINIMUM_SCORE_FOR_RATING_TABLE = 40;
    private final TestStatisticService testStatisticService;
    private final RatingTableRepository ratingTableRepository;

    @Autowired
    public RatingService(TestStatisticService testStatisticService, RatingTableRepository ratingTableRepository) {
        this.testStatisticService = testStatisticService;
        this.ratingTableRepository = ratingTableRepository;
    }

    /**
     * Метод, який збирає і повертає топ найкращих результатів статистики для всіх тестів
     * @return список всієї статистики для всіх ігор
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
                .map(x -> new UsernameScoreDTO(x.getTestStatistic().getUser().getUsername(), x.getTestStatistic().getScore()))
                .collect(Collectors.toList());
    }

    /**
     * Метод для пошуку та статистики тестів для кожного типу тестів, які увійшли в рейтингову таблицю
     * @return список результів методу getParticularGameStatisticStatisticForRatingTable() для кожного активного тесту
     * @see java.util.stream.Stream
     * @see UsernameScoreDTO
     * @see TestStatisticService
     */
    public Map<String, List<UsernameScoreDTO>> getRatingStatisticForEachTest(){
        return testStatisticService.getAllActiveTests().stream()
                .map(TestType::getTestType)
                .collect(Collectors.toMap(x -> x, this::getParticularGameStatisticStatisticForRatingTable));
    }
}
