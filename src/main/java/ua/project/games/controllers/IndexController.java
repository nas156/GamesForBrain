package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.games.entity.enums.CurrentStatus;
import ua.project.games.repository.TestTypeRepository;
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

    private final TestTypeRepository testTypeRepository;

    /**
     * Constructor for class with dependencies injection provided by Spring framework </br>
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param testTypeRepository        object of service that contains business logic for TestType class </br>
     *                                  об'єкт классу сервісу який містить бізнес логігу для классу TestType
     * @see UserService
     * @see ua.project.games.entity.TestType
     * @see Autowired
     */
    @Autowired
    public IndexController(TestTypeRepository testTypeRepository) {
        this.testTypeRepository = testTypeRepository;
    }
    /**
     * Method for handling get requests to / page </br>
     * Метод для обробки get запитів на сторінку /
     * @param model      object for adding attributes for model and than put it in template html</br>
     *                   об'єкт для додавання атрибутів до моделі с наступною обробкою в шалонах html
     * @return           html template</br>
     *                   html шаблон
     * @see GetMapping
     * @see Model
     * @see Principal
     */
    @GetMapping(value = "/")
    public String getIndexPage(Model model) {
        model.addAttribute("tests", testTypeRepository.findAllByCurrentStatus(CurrentStatus.Active));
        return "index";
    }
}
