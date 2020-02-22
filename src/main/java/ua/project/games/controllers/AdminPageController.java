package ua.project.games.controllers;

import org.apache.catalina.startup.ClassLoaderFactory;
import org.hibernate.cfg.beanvalidation.GroupsPerOperation;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.InterfaceMaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import ua.project.games.anotations.AdminRepo;
import ua.project.games.entity.User;
import ua.project.games.repository.TestStatisticRepository;
import ua.project.games.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.Principal;
import java.util.Arrays;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminPageController {


    final EntityManager entityManager;
    final DataSource dataSource;
    private final WebApplicationContext appContext;
    private final Set<?> classes;
    Repositories repositories = null;
    private User user = new User();


    public AdminPageController(DataSource dataSource, EntityManager entityManager, WebApplicationContext appContext) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
        this.appContext = appContext;
        this.classes = entityManager.getMetamodel().getEntities();
        this.repositories = new Repositories(appContext);
    }


    @GetMapping
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("classes", classes);
        return "/admin/adminIndex";
    }

    @GetMapping(value = "/{entity}")
    public String getRepeatNumbersTest(Principal principal, Model model, @PathVariable String entity){
        Reflections ref = new Reflections("");
        for (
                Class<?> cl : ref.getTypesAnnotatedWith(AdminRepo.class)) {
            AdminRepo findable = cl.getAnnotation(AdminRepo.class);
            if (findable.name().getSimpleName().equals(entity)) {
                Class<?> clazz = findable.name();
                System.out.println(clazz.toString());
                entityManager.getMetamodel().entity(clazz).getAttributes().stream().map(Attribute::getName).forEach(System.out::println);
                System.out.println(cl.getSimpleName());
            }
        }

        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", currentUserName);
        return "/admin/adminIndex";
    }
}



