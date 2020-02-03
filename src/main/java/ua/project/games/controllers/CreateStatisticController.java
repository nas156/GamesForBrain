package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.project.games.dto.UserDTO;
import ua.project.games.entity.TestStatistic;
import ua.project.games.entity.User;
import ua.project.games.service.TestStatisticService;
import ua.project.games.service.UserService;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/createStatistic")
public class    CreateStatisticController {
    private final TestStatisticService testStatisticService;
    private final UserService userService;

    public CreateStatisticController(TestStatisticService testStatisticService, UserService userService) {
        this.testStatisticService = testStatisticService;
        this.userService = userService;
    }

    @GetMapping
    public List<TestStatistic> getAll() {
        return testStatisticService.getAll();
    }

    @PostMapping
    @ResponseBody
    public User createStatistic(@RequestBody UserDTO user){
        return userService.loadUserByUsername(user.getUsername());
    }

}
