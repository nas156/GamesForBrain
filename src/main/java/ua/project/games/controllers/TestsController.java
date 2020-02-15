package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.games.service.TestStatisticService;
import ua.project.games.service.UserService;

import java.security.Principal;

@CrossOrigin
@Controller
@RequestMapping("tests")
public class TestsController {

    private final TestStatisticService testStatisticService;

    @Autowired
    public TestsController(TestStatisticService testStatisticService, UserService userService) {
        this.testStatisticService = testStatisticService;
    }

    @GetMapping(value = "/{testType}")
    public String getRepeatNumbersTest(Principal principal, Model model, @PathVariable String testType) {
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", currentUserName);
        return "games/" + testType;
    }
}
