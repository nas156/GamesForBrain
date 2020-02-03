package ua.project.games.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.entity.TestStatistic;
import ua.project.games.service.TestStatisticService;
import ua.project.games.service.UserService;

import java.awt.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin
@Controller
@RequestMapping("tests")
public class TestsController {

    private final TestStatisticService testStatisticService;
    private final UserService userService;

    @Autowired
    public TestsController(TestStatisticService testStatisticService, UserService userService) {
        this.testStatisticService = testStatisticService;
        this.userService = userService;
    }

    @GetMapping(value = "/repeatNumbers")
    public String getRepeatNumbersTest() {
        return "games/repeatNumbers";
    }

    @GetMapping(value = "/reactionGame")
    public String getReactionTest() {
        return "games/reactionGame";
    }

    @GetMapping(value = "/isPrevGame")
    public String getPrevGame() {
        return "games/isPrevGame";
    }


    @GetMapping(value = "/get/tests")
    @ResponseBody
    public List<TestStatistic> getAllTestsStatistic(Principal principal) {
        return testStatisticService.findAllTestsByUsername(principal.getName());
    }

    @GetMapping(value = "/repeatSequence")
    public String getRepeatSequence() {
        return "games/repeatSequence";
    }

}
