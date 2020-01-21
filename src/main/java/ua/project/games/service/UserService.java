package ua.project.games.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import ua.project.games.entity.enums.Role;
import ua.project.games.entity.User;
import ua.project.games.exceptions.InvalidUserException;
import ua.project.games.exceptions.UserExistsException;
import ua.project.games.repository.UserRepository;
import ua.project.games.validators.NewUserValidator;

import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void saveNewUser(User user) {
        //TODO inform the user about the replay email
        // TODO exception to endpoint
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            log.info("{Почтовый адрес уже существует}");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).get();
    }

    public void registerUser(User user, BindingResult result) throws InvalidUserException,UserExistsException {
        new NewUserValidator().validate(user, result);
        if(result.hasErrors()) {
            //TODO add normal exception
            throw new InvalidUserException("dont validate");
        }
        user.setActive(true);
        user.setRole(Role.USER);
        try {
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException e){
            result.rejectValue("username", "3", "login is already taken");
            throw new UserExistsException(user.getUsername());
        }

    }
}
