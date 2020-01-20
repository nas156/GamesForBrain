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
import ua.project.games.repository.UserRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/accounts")
@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;


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
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "accounts/registration";
        }

        user.setActive(true);
        user.setRole(Role.USER);
        userRepository.save(user);

        return "redirect:/accounts/login";
    }
}
