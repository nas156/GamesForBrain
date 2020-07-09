package ua.project.games.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("")
public class RatingByGameController {
    @GetMapping(value = "/rating")
    public String getChoseGamePage(Principal principal, Model model){
        return "rating/main";
    }


}
