package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import ua.project.games.entity.User;
import ua.project.games.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminPageController {


    final EntityManager entityManager;
    final DataSource dataSource;
    private final WebApplicationContext appContext;
    private final Set<?> classes;
    Repositories repositories = null;

    @Autowired
    private final UserRepository userRepository;

    public AdminPageController(DataSource dataSource, EntityManager entityManager, WebApplicationContext appContext, UserRepository userRepository) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
        this.appContext = appContext;
        this.classes = entityManager.getMetamodel().getEntities();
        this.repositories = new Repositories(appContext);
        this.userRepository = userRepository;
    }


    @GetMapping
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("classes", classes);
        return "/admin/adminIndex";
    }

    @GetMapping(value = "/User")
    public String getUsers (Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("users", userRepository.findAll());
        return "admin/adminUser";

    }

    @PostMapping(value = "/User/delete/{user_id}")
    public String deleteUser(@PathVariable String user_id) {
        User userToDelete = userRepository.findById(Long.parseLong(user_id)).get();
        userRepository.delete(userToDelete);
        return "redirect:/admin/User";
    }

    //TODO important!

//    @GetMapping(value = "/{entity}")
//    public String getRepeatNumbersTest(Principal principal, Model model, @PathVariable String entity){
//        Reflections ref = new Reflections("");
//        for (
//                Class<?> cl : ref.getTypesAnnotatedWith(AdminRepo.class)) {
//            AdminRepo findable = cl.getAnnotation(AdminRepo.class);
//            if (findable.name().getSimpleName().equals(entity)) {
//                Class<?> clazz = findable.name();
//                System.out.println(clazz.toString());
//                entityManager.getMetamodel().entity(clazz).getAttributes().stream().map(Attribute::getName).forEach(System.out::println);
//                System.out.println(cl.getSimpleName());
//            }
//        }
//
//        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
//        model.addAttribute("username", currentUserName);
//        return "/admin/adminIndex";
//    }
}



