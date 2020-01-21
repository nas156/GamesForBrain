package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.project.games.entity.User;
import ua.project.games.entity.enums.Role;
import ua.project.games.exceptions.UserExistsException;
import ua.project.games.repository.UserRepository;
import ua.project.games.service.UserService;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/accounts")
@Controller
public class AccountController {

    final
    UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/login")
    public String getLoginForm(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "accounts/login";
    }

    @GetMapping(value = "/registration")
    public String getRegistrationFrom(){
        return "accounts/registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        try {
            userService.registerUser(user);
        } catch (UserExistsException e) {
            return "redirect:/accounts/registration?error";
        }
        return "redirect:/accounts/login";
    }
}
