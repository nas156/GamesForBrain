package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.project.games.dto.TestStatisticDTO;
import ua.project.games.entity.User;
import ua.project.games.service.RatingService;
import ua.project.games.service.TestStatisticService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * class that controls get's ad post's requests to /createStatistic/** </br>
 *  Клас для контролю та відданю зображень на запроси які надходять до  /createStatistic/**
 * @see RestController
 * @see RequestMapping
 * @see CrossOrigin
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/createStatistic")
public class CreateStatisticController {

    private final TestStatisticService testStatisticService;
    private final RatingService ratingService;
    /**
     * Constructor for class with dependencies injection provided by Spring framework </br>
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param testStatisticService       object of service that contains business logic for TestStatistic entity </br>
     *                                   об'єкт классу сервісу який містить бізнес логігу для сутності TestStatistic
     * @param ratingService              object of service that contains business logic for RatingTable entity </br>
     *                                   об'єкт классу сервісу який містить бізнес логігу для сутності RatingTable
     * @see User
     * @see RatingService
     * @see TestStatisticService
     * @see Autowired
     */
    public CreateStatisticController(TestStatisticService testStatisticService, RatingService ratingService) {
        this.testStatisticService = testStatisticService;
        this.ratingService = ratingService;
    }

    /**
     * Method for handling post requests to /createStatistic </br>
     * Метод для обробки post запросів на сторінку /createStatistic
     * @param testStatisticDTO   dto object for TestStatistic entity
     *                           dto об'єкт для сутності TestStatistic
     * @see PostMapping
     * @see ResponseBody
     * @see TestStatisticDTO
     * @see TestStatisticService#createStatistic(TestStatisticDTO)
     */
    @PostMapping
    @ResponseBody
    public void createStatistic(@RequestBody TestStatisticDTO testStatisticDTO) {
        testStatisticService.createStatistic(testStatisticDTO);
    }

    //TODO change everywhere to /statisticByUserAndType

    /**
     * Method for handling get requests to /createStatistic/statisticByUserForRepeatNumbers </br>
     * Метод для обробки get запросів на сторінку /createStatistic/statisticByUserForRepeatNumbers
     * @param principal  interface object that represents User entity</br>
     *                   інтерфейс об'єкту що презентує сущність User
     * @param type       query string for test type</br>
     *                   параметр запросу для визначення типу тесту
     * @return           returns all statistic for user by test type in json format</br>
     *                   повертає всю статистику по користовачи для заданого типу тесту в json форматі
     * @see GetMapping
     * @see ResponseBody
     * @see RequestParam
     * @see Principal
     * @see TestStatisticService#getUserScoreForParticularTest(String, String)
     *
     */
    @GetMapping(value = "/statisticByUserForRepeatNumbers")
    @ResponseBody
    public List<Integer> getStatisticByUserAndType(Principal principal, @RequestParam(name = "type") String type) {
        return testStatisticService.getUserScoreForParticularTest(type, principal.getName());
    }

    /**
     * Method for handling post get  requests to /createStatistic/statisticByType </br>
     * Метод для обробки get post запросів на сторінку /createStatistic/statisticByType
     * @param type       query string for test type</br>
     *                   параметр запросу для визначення типу тесту
     * @return           returns all statistic by type in json format</br>
     *                   повертає всю статистику по заданому типу теста в json форматі
     * @see ResponseBody
     * @see RequestMapping
     * @see TestStatisticService#getStatisticForTest(String)
     * @see RequestParam
     */
    @RequestMapping(value = "/statisticByType")
    @ResponseBody
    public List<Integer> getRepeatNumbersStatistic(@RequestParam(name = "type") String type) {
        return testStatisticService.getStatisticForTest(type);
    }

    /**
     * Method for handling get requests to /createStatistic/getAllTestsStatisticByUser </br>
     * Метод для обробки get запросів на сторінку /createStatistic/getAllTestsStatisticByUser
     * @param principal  interface object that represents User entity</br>
     *                   інтерфейс об'єкту що презентує сущність User
     * @return           returns all test statistic by user in json format</br>
     *                   повертає всю стиститку по користувачу в json форматі
     * @see TestStatisticService#getAllTestsStatisticByUser(String)
     * @see GetMapping
     * @see ResponseBody
     * @see Principal
     */
    @GetMapping(value = "/getAllTestsStatisticByUser")
    @ResponseBody
    public Map<String, List<Integer>> getAllTestsStatisticByUser(Principal principal){
        return testStatisticService.getAllTestsStatisticByUser(principal.getName());
    }

    /**
     *  Method for handling post requests to /createStatistic/ratingTable </br>
     *  Метод для обробки post запросів на сторінку /createStatistic/ratingTable
     * @see PostMapping
     * @see RatingService#updateRatingTable()
     */
    @PostMapping(value = "/ratingTable")
    public void createRatingTable(){
        ratingService.updateRatingTable();
    }
}
