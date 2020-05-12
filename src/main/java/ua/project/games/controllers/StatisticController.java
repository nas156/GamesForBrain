package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.games.entity.User;
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

    /**
     *Method for handling get requests to /statistic page </br>
     * Метод для обробки get запитів на сторінку /statistic
     * @param model      object for adding attributes for model and than put it in template html</br>
     *                   об'єкт для додавання атрибутів до моделі с наступною обробкою в шалонах html
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
        return "statistic";
    }
}
