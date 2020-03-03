package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.games.entity.enums.Role;
import ua.project.games.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("")
public class StatisticController {

    private UserService userService;

    @Autowired
    public StatisticController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/statistic")
    public String getIndexPage(Principal principal, Model model){
//        if (!(principal==null)){
//            Role role = userService.getUserRole(principal.getName());
//            switch (role){
//                case USER:
//
//                case ADMIN:
//                    //redirect to admin page
//            }
//        }
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", currentUserName);
        return "statistic";
    }
}
