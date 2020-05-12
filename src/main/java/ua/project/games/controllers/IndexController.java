package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.games.entity.enums.CurrentStatus;
import ua.project.games.entity.enums.Role;
import ua.project.games.repository.TestTypeRepository;
import ua.project.games.service.UserService;

import javax.sql.DataSource;
import java.security.Principal;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    //todo change repository to service
    private final TestTypeRepository testTypeRepository;

    /**
     * Constructor for class with dependencies injection provided by Spring framework </br>
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param userService               object of service that contains business logic for User class </br>
     *                                  об'єкт классу сервісу який містить бізнес логігу для классу User
     * @param testTypeRepository        object of service that contains business logic for TestType class </br>
     *                                  об'єкт классу сервісу який містить бізнес логігу для классу TestType
     * @see UserService
     * @see ua.project.games.entity.TestType
     * @see Autowired
     */
    @Autowired
    public IndexController(UserService userService, TestTypeRepository testTypeRepository) {
        this.userService = userService;
        this.testTypeRepository = testTypeRepository;
    }
    /**
     * Method for handling get requests to / page </br>
     * Метод для обробки get запросів на сторінку /
     * @param model      object for adding attributes for model and than put it in template html</br>
     *                   обьект для додавання атрибутів до моделі с наступною обробкою в шалонах html
     * @param principal  interface object that represents User entity</br>
     *                   інтерфейс об'єкту що презентує сущність User
     * @return           html template</br>
     *                   html шаблон
     * @see GetMapping
     * @see Model
     * @see Principal
     */
    @GetMapping(value = "/")
    public String getIndexPage(Principal principal, Model model) {
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("tests", testTypeRepository.findAllByCurrentStatus(CurrentStatus.Active));
        model.addAttribute("username", currentUserName);
        return "index";
    }
}
