package ua.project.games.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.project.games.entity.User;
import ua.project.games.exceptions.InvalidUserException;
import ua.project.games.exceptions.UserExistsException;
import ua.project.games.service.RegistrationService;
import ua.project.games.service.TestStatisticService;
import ua.project.games.service.TestTypeService;
import ua.project.games.service.UserService;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Set;


/**
 * Class that controls get's ad post's requests to /admin/** </br>
 * Клас для контролю та відданю зображень на запроси які надходять до  /admin/**
 *
 * @see Controller
 * @see RequestMapping
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminPageController {


    final EntityManager entityManager;
    private final Set<?> classes;
    private final TestStatisticService testStatisticService;
    private final UserService userService;
    private final TestTypeService testTypeService;
    private final RegistrationService registrationService;

    /**
     * Constructor for class with dependencies injection provided by Spring framework </br>
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     *
     * @param entityManager        manager for all Entity's in project</br>
     *                             менеджер всіх сутностей в проекті
     * @param testStatisticService object of service that contains business logic for TestStatistic entity </br>
     *                             об'єкт классу сервісу який містить бізнес логігу для сутності TestStatistic
     * @param userService          object of service that contains business logic for User class </br>
     *                             об'єкт классу сервісу який містить бізнес логігу для классу User
     * @param testTypeService      object of service that contains business logic for TestType entity</br>
     *                             об'єкт классу сервісу який містить бізнес логігу для TestType сутності
     * @param registrationService  object of service that contains business logic for registration </br>
     *                             об'єкт классу сервісу який містить бізнес логігу для реєстрування користовачів
     */
    public AdminPageController(EntityManager entityManager, TestStatisticService testStatisticService, UserService userService, TestTypeService testTypeService, RegistrationService registrationService) {
        this.entityManager = entityManager;
        this.classes = entityManager.getMetamodel().getEntities();
        this.testStatisticService = testStatisticService;
        this.userService = userService;
        this.testTypeService = testTypeService;
        this.registrationService = registrationService;
    }

    /**
     * Method for handling get requests to /admin </br>
     * Метод для обробки get запросів на сторінку /admin
     *
     * @param model     object for adding attributes for model and than put it in template html</br>
     *                  обьект для додавання атрибутів до моделі с наступною обробкою в шалонах html
     * @param principal interface object that represents User entity</br>
     *                  інтерфейс об'єкту що презентує сущність User
     * @return html template</br>
     * html шаблон
     * @see GetMapping
     * @see Model
     * @see Principal
     */
    @GetMapping
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("classes", classes);
        return "/admin/adminIndex";
    }

    /**
     * Method for handling get requests to /admin/User </br>
     * Метод для обробки get запросів на сторінку /admin/User
     *
     * @param search    optional query string for searching the user</br>
     *                  необов'язковий параметр запиту для пошуку користувача
     * @param model     object for adding attributes for model and than put it in template html</br>
     *                  обьект для додавання атрибутів до моделі с наступною обробкою в шалонах html
     * @param principal interface object that represents User entity</br>
     *                  інтерфейс об'єкту що презентує сущність User
     * @return          html template</br>
     *                  html шаблон
     * @see GetMapping
     * @see Model
     * @see Principal
     */
    @GetMapping(value = "/User")
    public String getUsers(@RequestParam Optional<String> search, Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("users", userService.findByUsername(search.orElse("_")).orElseGet(userService::getAll));
        return "admin/user/adminUser";
    }

    /**
     * Method for handling post requests to /admin/User/delete/{user_id} </br>
     * Метод для обробки post запросів на сторінку /admin/User/delete/{user_id}
     *
     * @param user_id   String that contains in path as {user_id}</br>
     *                  Строка що міститься в шляху як {usr_id}
     * @return          redirect to /admin/User page </br>
     *                  перенаправлення на сторінку /admin/User
     */
    @PostMapping(value = "/User/delete/{user_id}")
    public String deleteUser(@PathVariable String user_id) {
        User userToDelete = userService.getById(Long.parseLong(user_id));
        testStatisticService.deleteAllByUser(userToDelete);
        userService.delete(userToDelete);
        return "redirect:/admin/User";
    }

    /**
     * Method for handling post requests to /admin/User/clearStat/{user_id} </br>
     * Метод для обробки post запросів на сторінку /admin/User/clearStat/{user_id}
     *
     * @param user_id    String that contains in path as {user_id}</br>
     *                   Строка що міститься в шляху як {usr_id}
     * @return           redirect to /admin/User page </br>
     *                    перенаправлення на сторінку /admin/User
     */
    @PostMapping(value = "/User/clearStat/{user_id}")
    public String clearUserStat(@PathVariable String user_id) {
        User userToDelete = userService.getById(Long.parseLong(user_id));
        testStatisticService.deleteAllByUser(userToDelete);
        return "redirect:/admin/User";
    }

    /**
     * Method for handling get requests to /admin/User/update/{user_id} </br>
     * Метод для обробки get запросів на сторінку /admin/User/update/{user_id}
     * @param user_id    String that contains in path as {user_id}</br>
     *                   Строка що міститься в шляху як {usr_id}
     * @param model     object for adding attributes for model and than put it in template html</br>
     *                  обьект для додавання атрибутів до моделі с наступною обробкою в шалонах html
     * @param principal interface object that represents User entity</br>
     *                  інтерфейс об'єкту що презентує сущність User
     * @param user       object for binding User object to html form</br>
     *                   об'єкт для зв'язування об'єкту User з html формою
     * @return          html template</br>
     *                  html шаблон
     */
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

    /**
     * Method for handling post requests to /admin/User/update/{user_id} </br>
     * Метод для обробки post запросів на сторінку /admin/User/update/{user_id}
     * @param user_id    String that contains in path as {user_id}</br>
     *                   Строка що міститься в шляху як {usr_id}
     * @param user       object for binding User object to html form</br>
     *                   об'єкт для зв'язування об'єкту User з html формою
     * @return          redirect to /admin/User page </br>
     *                  перенаправлення на сторінку /admin/User
     */
    @PostMapping(value = "/User/update/{user_id}")
    public String updateUser(@PathVariable String user_id, @Valid @ModelAttribute("usr") User user) {
        User userToUpdate = userService.getById(Long.parseLong(user_id));
        userService.updateUser(userToUpdate, user);
        return "redirect:/admin/User";
    }

    /**
     * Method for handling get requests to /admin/User.add </br>
     * Метод для обробки get запросів на сторінку /admin/User/add
     * @param model     object for adding attributes for model and than put it in template html</br>
     *                  обьект для додавання атрибутів до моделі с наступною обробкою в шалонах html
     * @param principal interface object that represents User entity</br>
     *                  інтерфейс об'єкту що презентує сущність User
     * @param user       object for binding User object to html form</br>
     *                   об'єкт для зв'язування об'єкту User з html формою
     * @return          html template</br>
     *                  html шаблон
     */
    @GetMapping(value = "/User/add")
    public String addUser(Model model, Principal principal, @ModelAttribute("usr") User user) {
        model.addAttribute("username", principal.getName());
        return "admin/user/addUser";
    }

    /**
     * Method for handling post requests to /admin/User/add </br>
     * Метод для обробки post запросів на сторінку /admin/User/add
     * @param model     object for adding attributes for model and than put it in template html</br>
     *                  обьект для додавання атрибутів до моделі с наступною обробкою в шалонах html
     * @param user       object for binding User object to html form</br>
     *                   об'єкт для зв'язування об'єкту User з html формою
     * @param result     object for registration errors in form inputs</br>
     *                   об'єкт для реєстрації помило в полях html форми
     * @return          if adding of user is success redirect to /admin/User page else redirect to /admin/User/add</br>
     *                  якщо користувача успішлно довблено то перенаправлення на /admin/User в іншому разі перпревлення на /admin/User/add
     */
    @PostMapping(value = "/User/add")
    public String addUser(Model model, @ModelAttribute("usr") User user, BindingResult result) {
        user.setConfirmPassword(user.getPassword());
        try {
            registrationService.registerUser(user, result);
        } catch (UserExistsException | InvalidUserException e) {
            log.warn("Invalid User");
            return "redirect:/admin/User/add";
        }
        return "redirect:/admin/User";
    }

    /**
     * Method for handling get requests to /admin/TestType </br>
     * Метод для обробки get запросів на сторінку /admin/TestType
     * @param model     object for adding attributes for model and than put it in template html</br>
     *                  обьект для додавання атрибутів до моделі с наступною обробкою в шалонах html
     * @param principal interface object that represents User entity</br>
     *                  інтерфейс об'єкту що презентує сущність User
     * @return          html template</br>
     *                  html шаблон
     */
    @GetMapping(value = "/TestType")
    public String geTestTypes(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("testTypes", testTypeService.getAll());
        return "admin/testType/adminTestType";
    }

    /**
     * Method for handling post requests to /admin/TestType/delete/{test_id} </br>
     * Метод для обробки post запросів на сторінку /admin/TestType/delete/{test_id}
     * @param test_id    String that contains in path as {test_id}</br>
     *                   Строка що міститься в шляху як {test_id}
     * @return           redirect to /admin/TestType</br>
     *                   переправлення на /admin/TestType
     */
    @PostMapping(value = "/TestType/delete/{test_id}")
    public String deleteTestType(@PathVariable String test_id) {
        testTypeService.deleteTestbyId(Long.parseLong(test_id));
        return "redirect:/admin/TestType";
    }

    /**
     * Method for handling get requests to /admin/TestType/activate/{test_id} </br>
     * Метод для обробки get запросів на сторінку /admin/TestType/activate/{test_id}
     * @param test_id    String that contains in path as {test_id}</br>
     *                   Строка що міститься в шляху як {test_id}
     * @return           redirect to /admin/TestType</br>
     *                   переправлення на /admin/TestType
     */
    @PostMapping(value = "/TestType/activate/{test_id}")
    public String activateTestType(@PathVariable String test_id) {
        testTypeService.activateTestById(Long.parseLong(test_id));
        return "redirect:/admin/TestType";
    }
}



