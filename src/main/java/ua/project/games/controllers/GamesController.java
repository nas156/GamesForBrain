package ua.project.games.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "games")
public class GamesController {
    @GetMapping(value = "/repeat-numbers-game")
    public String game1(){
        return "games/repeatNumbers";
    }
}
