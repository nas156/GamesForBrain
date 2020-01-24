package ua.project.games.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tests")
public class TestsController {

    @GetMapping(value="/repeatNumbers")
    public String getRepeatNumbers () {
        return "games/repeatNumbers";
    }
}
