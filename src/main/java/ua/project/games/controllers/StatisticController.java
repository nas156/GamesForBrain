package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.games.entity.User;
import ua.project.games.entity.enums.Role;
import ua.project.games.service.RegistrationService;
import ua.project.games.service.UserService;

import java.security.Principal;

/**
 * Class that controls get's ad post's requests to /statistic </br>
 *  Клас для контролю та відданю зображень на запроси які надходять до  /statistic
 * @see Controller
 * @see RequestMapping
 */
@Controller
@RequestMapping("")
public class StatisticController {

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
    public StatisticController(UserService userService) {
        this.userService = userService;
    }

    /**
     *Method for handling get requests to /accounts/registration page </br>
     * Метод для обробки get запросів на сторінку accounts/registration
     * @param principal interface object that represents User entity</br>
     *                  інтерфейс об'єкту що презентує сущність User
     * @return          html template</br>
     *                  html шаблон
     * @see GetMapping
     * @see Model
     * @see Principal
     */
    @GetMapping(value = "/statistic")
    public String getIndexPage(Principal principal, Model model){
        model.addAttribute("username", principal.getName());
        return "statistic";
    }
}
