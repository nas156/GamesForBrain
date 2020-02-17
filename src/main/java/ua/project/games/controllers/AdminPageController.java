package ua.project.games.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import ua.project.games.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.sql.DataSource;
import java.security.Principal;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    final EntityManager entityManager;
    final DataSource dataSource;
    final Set<EntityType<?>> classes;
    private final WebApplicationContext appContext;

    Repositories repositories = null;
    private User user = new User();


    public AdminPageController(DataSource dataSource, EntityManager entityManager, WebApplicationContext appContext) {
        this.dataSource = dataSource;
        this.entityManager = entityManager;
        this.classes = entityManager.getMetamodel().getEntities();
        this.appContext = appContext;
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
        for(EntityType<?> clazz : classes){
            if(clazz.toString().equals(entity)){
                clazz.getAttributes().stream().map(Attribute::getName).forEach(System.out::println);
            }
        }
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", currentUserName);
        return "/admin/adminIndex";
    }
}

