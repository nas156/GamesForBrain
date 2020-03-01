package ua.project.games.controllers;

import org.apache.catalina.startup.ClassLoaderFactory;
import org.aspectj.apache.bcel.util.ClassPath;
import org.hibernate.cfg.beanvalidation.GroupsPerOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cglib.proxy.InterfaceMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
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
    public String getRepeatNumbersTest(Principal principal, Model model, @PathVariable String entity) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(EntityType<?> clazz : classes){
            if(clazz.toString().equals(entity)){
                clazz.getAttributes().stream().map(Attribute::getName).forEach(System.out::println);
//                Class<?> repo = Class.forName("ua.project.games.repository."+clazz.getName()+"Repository");
//                Method getAll = repo.getMethod("findAll");
//                System.out.println(getAll.invoke(repositories.getRepositoryFor(
//                        Class.forName("ua.project.games.entity."+clazz.getName())).get()));
                ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
                scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
                for (BeanDefinition bd: scanner.findCandidateComponents("ua.project.games")){
                    System.out.println(bd.getBeanClassName());
                    }
            }
        }
        final String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", currentUserName);
        return "/admin/adminIndex";
    }
}

