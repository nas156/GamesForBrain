package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.User;
import ua.project.games.repository.TestStatisticRepository;
import ua.project.games.service.UserService;

import java.util.List;

@Controller
@RequestMapping("tests")
public class TestsController {

    @Autowired
    private TestStatisticRepository repo;
    @Autowired
    private UserService serv;

    @GetMapping(value = "/repeatNumbers")
    public String getRepeatNumbers() {
        return "games/repeatNumbers";
    }


    @GetMapping(value = "/reactionGame")
    public String getReactionGame() {
        return "games/reactionGame";
    }

    @GetMapping(value = "/get/tets")
    @ResponseBody
    public List<TestStatistic> getRactionGame() {
        User usr = serv.loadUserByUsername("pavel");
        return repo.findAllByUser(usr);
    }
}
