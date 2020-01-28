package ua.project.games.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.project.games.entity.TestStatistic;
import ua.project.games.repository.TestStatisticRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestStatisticService {
    private final TestStatisticRepository testStatisticRepository;

    public TestStatisticService(TestStatisticRepository testStatisticRepository) {
        this.testStatisticRepository = testStatisticRepository;
    }

    //TODO optional checking
    public List<TestStatistic> findAllTestsByUsername(String username){
        return testStatisticRepository.findAllByUser_Username(username).get();
    }
}
