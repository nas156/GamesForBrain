package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.project.games.dto.UsernameScoreDTO;
import ua.project.games.service.RatingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/ratingTable")
public class RatingTableController {
    final
    RatingService ratingService;

    @Autowired
    public RatingTableController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(value = "/getAllTests")
    @ResponseBody
    public Map<String, List<UsernameScoreDTO>> getAllTests(){
        return ratingService.getRatingStatisticForEachTest();
    }

    @GetMapping(value = "/getForTest")
    @ResponseBody
    public List<UsernameScoreDTO> getRatingForTest(@RequestParam(name = "type") String testType){
        return ratingService.getParticularGameStatisticStatisticForRatingTable(testType);
    }

}
