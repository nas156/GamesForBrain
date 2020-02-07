package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.project.games.entity.TestStatistic;
import ua.project.games.service.TestStatisticService;
import ua.project.games.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping("tests")
public class TestsController {

    private final TestStatisticService testStatisticService;

    @Autowired
    public TestsController(TestStatisticService testStatisticService, UserService userService) {
        this.testStatisticService = testStatisticService;
    }

    @GetMapping(value = "/repeatNumbers")
    public String getRepeatNumbersTest(Principal principal, Model model) {
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", currentUserName);
        return "games/repeatNumbers";
    }


    @GetMapping(value = "/reactionGame")
    public String getReactionTest(Principal principal, Model model) {
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", currentUserName);
        return "games/reactionGame";
    }

    @GetMapping(value = "/isPrevGame")
    public String getPrevGame(Principal principal, Model model) {
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", currentUserName);
        return "games/isPrevGame";
    }

    @GetMapping(value = "/repeatSequence")
    public String getRepeatSequence(Principal principal, Model model) {
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", currentUserName);
        return "games/repeatSequence";
    }

    @GetMapping(value = "/countGreen")
    public String getCountGreen(Principal principal, Model model) {
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", currentUserName);
        return "games/countGreenGame";
    }
}
