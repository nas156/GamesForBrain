package ua.project.games.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.entity.TestStatistic;
import ua.project.games.service.TestStatisticService;

import java.util.List;

@CrossOrigin
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
    @ResponseBody
    public void createStatistic(@RequestBody TestStatisticDTO testStatisticDTO) {
        testStatisticService.createStatistic(testStatisticDTO);
        System.out.println(testStatisticDTO);
    }
}
