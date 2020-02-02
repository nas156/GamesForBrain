package ua.project.games.controllers;

import org.springframework.web.bind.annotation.*;
import ua.project.games.entity.TestStatistic;
import ua.project.games.service.TestStatisticService;

import java.util.List;

@RestController
@RequestMapping(value = "/createStatistic")
public class CreateStatisticController {
    private final TestStatisticService testStatisticService;

    public CreateStatisticController(TestStatisticService testStatisticService) {
        this.testStatisticService = testStatisticService;
    }

    @GetMapping
    public List<TestStatistic> getAll() {
        return testStatisticService.getAll();
    }

    @PostMapping
    public void createStatistic(){
        System.out.println(1);
    }

}
