package ua.project.games.controllers;

import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import ua.project.games.entity.User;
import ua.project.games.service.TestStatisticService;
import ua.project.games.service.TestTypeService;
import ua.project.games.service.UserService;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminPageController {


    final EntityManager entityManager;
    final DataSource dataSource;
    private final Set<?> classes;
    Repositories repositories;
    private final TestStatisticService testStatisticService;
    private final UserService userService;
    private final TestTypeService testTypeService;

    public AdminPageController(DataSource dataSource, EntityManager entityManager, WebApplicationContext appContext, TestStatisticService testStatisticService, UserService userService, TestTypeService testTypeService) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
        this.classes = entityManager.getMetamodel().getEntities();
        this.repositories = new Repositories(appContext);
        this.testStatisticService = testStatisticService;
        this.userService = userService;
        this.testTypeService = testTypeService;
    }


    @GetMapping
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("classes", classes);
        return "/admin/adminIndex";
    }

    @GetMapping(value = "/User")
    public String getUsers(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("users", userService.getAll());
        return "admin/user/adminUser";

    }

    @PostMapping(value = "/User/delete/{user_id}")
    public String deleteUser(@PathVariable String user_id) {
        User userToDelete = userService.getById(Long.parseLong(user_id));
        testStatisticService.deleteAllbyUser(userToDelete);
        userService.delete(userToDelete);
        return "redirect:/admin/User";
    }

    @GetMapping(value = "/User/update/{user_id}")
    public String updateUser(@PathVariable String user_id, Model model, Principal principal, @ModelAttribute("usrr") User user) {
        model.addAttribute("username", principal.getName());
        User userToUpdate = userService.getById(Long.parseLong(user_id));
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String age = format1.format(userToUpdate.getAge().getTime());
        model.addAttribute("user", userToUpdate);
        model.addAttribute("age", age);
        return "admin/user/updateUser";
    }

    @PostMapping(value = "/User/update/{user_id}")
    public String updateUser(@PathVariable String user_id, @Valid @ModelAttribute("usr") User user) {
        User userToUpdate = userService.getById(Long.parseLong(user_id));
        userService.updateUser(userToUpdate, user);
        return "redirect:/admin/User";
    }

    @GetMapping(value = "/TestType")
    public String geTestTypes(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("testTypes", testTypeService.getAll());
        return "admin/testType/adminTestType";
    }

    @PostMapping(value = "/TestType/delete/{test_id}")
    public String deleteTestType(@PathVariable String test_id) {
        testTypeService.deleteTestbyId(Long.parseLong(test_id));
        return "redirect:/admin/TestType";
    }

    @PostMapping(value = "/TestType/activate/{test_id}")
    public String activateTestType(@PathVariable String test_id) {
        testTypeService.activateTestById(Long.parseLong(test_id));
        return "redirect:/admin/TestType";
    }
}



