package ua.project.games.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.project.games.entity.RatingTable;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.TestType;
import ua.project.games.repository.RatingTableRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<TestStatistic> getAllTestStatisticForRatingTable(){
        return testStatisticService.getAllActiveTests().stream().map(TestType::getTestType)
                .flatMap(x -> testStatisticService.getTopScoresForATest(x, MINIMUM_SCORE_FOR_RATING_TABLE).stream())
                .collect(Collectors.toList());
    }

    private List<RatingTable> getAllRatingFields(){
       return getAllTestStatisticForRatingTable().stream()
               .map(RatingTable::new)
               .collect(Collectors.toList());
    }

    @Transactional
    @Scheduled(cron = "0 0 6 * * *")
    public void updateRatingTable(){
        ratingTableRepository.deleteAll();
        ratingTableRepository.saveAll(getAllRatingFields());
    }

}