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
    private final TestTypeRepository testTypeRepository;

    /**
     * Constructor for class with dependencies injection provided by Spring framework </br>
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param userService               object of service that contains business logic for User class </br>
     *                                  об'єкт классу сервісу який містить бізнес логігу для классу User
     * @see UserService
     * @see Autowired
     */
    @Autowired
    public IndexController(UserService userService, TestTypeRepository testTypeRepository) {
        this.userService = userService;
        this.testTypeRepository = testTypeRepository;
    }

    @GetMapping(value = "/")
    public String getIndexPage(Principal principal, Model model) throws SQLException {
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("tests", testTypeRepository.findAllByCurrentStatus(CurrentStatus.Active));
        model.addAttribute("username", currentUserName);
        return "index";
    }
}
