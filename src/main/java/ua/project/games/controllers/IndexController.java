package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.games.entity.User;
import ua.project.games.entity.enums.Role;
import ua.project.games.service.UserService;

import java.security.Principal;

/**
 * Class that controls get's ad post's requests to / </br>
 *  Клас для контролю та відданю зображень на запроси які надходять до  /
 * @see Controller
 * @see RequestMapping
 */
@Controller
@RequestMapping("")
public class IndexController {

    private UserService userService;

    /**
     * Constructor for class with dependencies injection provided by Spring framework </br>
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param userService               object of service that contains business logic for User class </br>
     *                                  об'єкт классу сервісу який містить бізнес логігу для классу User
     * @see UserService
     * @see User
     * @see Autowired
     */
    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    //TODO add special permissions for user and admin in config class
    public String getIndexPage(Principal principal, Model model){
        if (!(principal==null)){
            Role role = userService.getUserRole(principal.getName());
            switch (role){
                case USER:
                    final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
                    model.addAttribute("username", currentUserName);
                case ADMIN:
                    //redirect to admin page
            }
        }
        return "index";
    }
}
