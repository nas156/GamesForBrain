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
public class IndexController {

    private UserService userService;

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    //TODO add special permissions for user and admin in config class
    public String getIndexPage(Principal principal){
        if (!(principal==null)){
            Role role = userService.getUserRole(principal.getName());
            switch (role){
                case USER:
                    // redirect to user page
                case ADMIN:
                    //redirect to admin page
            }
        }
        return "index";
    }
}
