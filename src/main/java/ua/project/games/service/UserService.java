package ua.project.games.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.function.Supplier;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).get();
    }



    public Role getUserRole(String username) {
        return userRepository.findByUsername(username).orElse(new User()).getRole();
    }
}
