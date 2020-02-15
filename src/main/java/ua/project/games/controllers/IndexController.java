package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.project.games.entity.enums.Role;
import ua.project.games.repository.TestTypeRepository;
import ua.project.games.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("")
public class IndexController {

    private UserService userService;
    private final TestTypeRepository testTypeRepository;

    @Autowired
    public IndexController(UserService userService, TestTypeRepository testTypeRepository) {
        this.userService = userService;
        this.testTypeRepository = testTypeRepository;
    }

    @GetMapping(value = "/")
    //TODO add special permissions for user and admin in config class
    public String getIndexPage(Principal principal, Model model){
//        if (!(principal==null)){
//            Role role = userService.getUserRole(principal.getName());
//            switch (role){
//                case USER:
//
//                case ADMIN:
//                    //redirect to admin page
//            }
//        }
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("tests", testTypeRepository.findAll());
        model.addAttribute("username", currentUserName);
        return "index";
    }
}
