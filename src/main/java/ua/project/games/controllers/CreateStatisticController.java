package ua.project.games.controllers;

import org.springframework.web.bind.annotation.*;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.service.RatingService;
import ua.project.games.service.TestStatisticService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/createStatistic")
public class CreateStatisticController {

    private final TestStatisticService testStatisticService;
    private final RatingService ratingService;

    public CreateStatisticController(TestStatisticService testStatisticService, RatingService ratingService) {
        this.testStatisticService = testStatisticService;
        this.ratingService = ratingService;
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

    @PostMapping(value = "/ratingTable")
    public void createRatingTable(){
        ratingService.updateRatingTable();
    }
}
