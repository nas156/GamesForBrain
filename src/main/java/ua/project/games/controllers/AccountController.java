package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.project.games.entity.User;
import ua.project.games.entity.enums.Role;
import ua.project.games.exceptions.InvalidUserException;
import ua.project.games.exceptions.UserExistsException;
import ua.project.games.service.RegistrationService;
import ua.project.games.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Class for rendering login and registration template. Controls get's ad post's requests to /accounts/** </br>
 *  Клас для контролю та відданю зображень на запроси які надходять до  /accounts/**
 * @see Controller
 * @see RequestMapping
 */
@RequestMapping("/accounts")
@Controller
public class AccountController {
    final UserService userService;
    private RegistrationService registrationService;

    /**
     * Constructor for class with dependencies injection provided by Spring framework </br>
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param userService               object of service that contains business logic for User class </br>
     *                                  об'єкт классу сервісу який містить бізнес логігу для классу User
     * @param registrationService       object of service that contains business logic for registration </br>
     *                                  об'єкт классу сервісу який містить бізнес логігу для реєстрування користовачів
     * @see RegistrationService
     * @see UserService
     * @see User
     * @see Autowired
     */
    @Autowired
    public AccountController(UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }


    /**
     * Method for handling get requests to /accounts/login page </br>
     * Метод для обробки get запросів на сторінку accounts/login
     * @param error      optional query string for rendering page with error message </br>
     *                   необов'язковий параметр запросу для віддання сторінки з повідомленням про помилку
     * @param logout     optional query string for rendering page with error logout message </br>
     *                   необов'язковий параметр запросу для віддання сторінки з повідомленням про logout
     * @param model      object for adding attributes for model and than put it in template html</br>
     *                   обьект для додавання атрибутів до моделі с наступною обробкою в шалонах html
     * @return           html template</br>
     *                   html шаблон
     * @see Model
     * @see GetMapping
     * @see RequestParam
     */
    @GetMapping(value = "/login")
    public String getLoginForm(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "accounts/login";
    }

    /**
     *Method for handling get requests to /accounts/registration page </br>
     * Метод для обробки get запросів на сторінку accounts/registration
     * @param user      object for binding User object to html form</br>
     *                  об'єкт для зв'язування об'єкту User з html формою
     * @return          html template</br>
     *                  html шаблон
     * @see GetMapping
     * @see User
     */
    @GetMapping(value = "/registration")
    public String getRegistrationFrom(@ModelAttribute("user") User user){
        return "accounts/registration";
    }

    /**
     *Method for handling post requests to /accounts/registration page </br>
     *Метод для обробки post запросів на сторінку registration/login
     * @param user       object for binding User object to html form</br>
     *                   об'єкт для зв'язування об'єкту User з html формою
     * @param model      object for adding attributes for model and than put it in template html</br>
     *                   обьект для додавання атрибутів до моделі с наступною обробкою в шалонах html
     * @param result     object for registration errors in form inputs</br>
     *                   об'єкт для реєстрації помило в полях html форми
     * @return           if registration failed returns html registration template else redirect to login page</br>
     *                   якщо реєстрація невалідна повертає шаблон реєстрації інкше перенаправляє н сторінку логіну
     * @see ModelAttribute
     * @see RequestMapping
     * @see User
     * @see RegistrationService
     * @see BindingResult
     * @see Model
     * @see UserExistsException
     * @see InvalidUserException
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("user") User user, Model model, BindingResult result) {
        try {
            registrationService.registerUser(user, result);
        } catch (UserExistsException | InvalidUserException e) {
            return "accounts/registration";
        }
        return "redirect:/accounts/login";
    }
}
