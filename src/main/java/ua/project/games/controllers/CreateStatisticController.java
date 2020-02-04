package ua.project.games.controllers;

import org.springframework.web.bind.annotation.*;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.enums.TestType;
import ua.project.games.service.TestStatisticService;

import java.security.Principal;
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

    //TODO html pages and optional processing
    @RequestMapping(value = "/statisticByUserForRepeatNumbers")
    @ResponseBody
    public List<TestStatistic> getStatisticByUserAndType(Principal principal){
        return testStatisticService.getUserStatisticForParticularTest(TestType.RepeatNumbersTest, principal.getName());
    }

    @RequestMapping(value = "/repeatNumbersStatistic")
    @ResponseBody
    public List<TestStatistic> getRepeatNumbersStatistic(){
        return testStatisticService.getStatisticForTest(TestType.RepeatNumbersTest);
    }

}
