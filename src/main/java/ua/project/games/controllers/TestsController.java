package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.games.service.TestStatisticService;

import java.security.Principal;

/**
 * Class that controls get's ad post's requests to /tests/** </br>
 *  Клас для контролю та відданю зображень на запроси які надходять до  /tests/**
 * @see Controller
 * @see RequestMapping
 * @see CrossOrigin
 */
@CrossOrigin
@Controller
@RequestMapping("tests")
public class TestsController {


    /**
     *Method for handling get requests to /{testType} </br>
     * Метод для обробки get запитів на сторінку /{testType}
     * @param testType   String that goes after /tests/{testType}</br>
     *                   Строка що йде після /tests/{testType}
     * @return           html template</br>
     *                   html шаблон
     * @see GetMapping
     * @see Model
     * @see Principal
     * @see PathVariable
     */
    @GetMapping(value = "/{testType}")
    public String getRepeatNumbersTest(@PathVariable String testType) {
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return "games/" + testType;
    }
}
