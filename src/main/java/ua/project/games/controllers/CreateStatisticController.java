package ua.project.games.controllers;

import org.springframework.web.bind.annotation.*;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.service.TestStatisticService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/createStatistic")
public class CreateStatisticController {

    private final TestStatisticService testStatisticService;

    public CreateStatisticController(TestStatisticService testStatisticService) {
        this.testStatisticService = testStatisticService;
    }

    @PostMapping
    @ResponseBody
    public void createStatistic(@RequestBody TestStatisticDTO testStatisticDTO) {
        testStatisticService.createStatistic(testStatisticDTO);
        System.out.println(testStatisticDTO);
    }

    //TODO change everywhere to /statisticByUserAndType
    @GetMapping(value = "/statisticByUserForRepeatNumbers")
    @ResponseBody
    public List<Integer> getStatisticByUserAndType(Principal principal, @RequestParam(name = "type") String type) {
        return testStatisticService.getUserScoreForParticularTest(type, principal.getName());
    }

    @RequestMapping(value = "/statisticByType")
    @ResponseBody
    public List<Integer> getRepeatNumbersStatistic(@RequestParam(name = "type") String type) {
        return testStatisticService.getStatisticForTest(type);
    }

    @GetMapping(value = "/getAllTestsStatisticByUser")
    @ResponseBody
    public Map<String, List<Integer>> getAllTestsStatisticByUser(Principal principal){
        return testStatisticService.getAllTestsStatisticByUser(principal.getName());
    }
}
