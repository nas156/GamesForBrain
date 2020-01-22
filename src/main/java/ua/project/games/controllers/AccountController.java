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

@RequestMapping("/accounts")
@Controller
public class AccountController {

    final UserService userService;
    private RegistrationService registrationService;


    @Autowired
    public AccountController(UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
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
    public String getRegistrationFrom(@ModelAttribute("user") User user){
        return "accounts/registration";
    }

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
