package ua.project.games.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ua.project.games.entity.User;
import ua.project.games.entity.enums.Role;
import ua.project.games.exceptions.InvalidUserException;
import ua.project.games.exceptions.UserExistsException;
import ua.project.games.repository.UserRepository;
import ua.project.games.validators.NewUserValidator;

/**
 * Клас, для описання методів, які пов'язані з реєстрацією користувачів, створенням нових об'єктів класу User та запису їх в базу даних
 */
@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Метод, який записує унікального юзера в таблицю usr. Викидає UserExistsException, якщо у переданого в пераметрах класу User не унікльний юзернейм.
     * Викидає InvalidUserException, якщо переданий в параметрах клас User не проходить валідування в методі validate() класу NewUserValidator
     * @param user
     * @param result
     * @throws InvalidUserException
     * @throws UserExistsException
     */
    public void registerUser(User user, BindingResult result) throws InvalidUserException, UserExistsException {
        new NewUserValidator().validate(user, result);
        if(result.hasErrors()) {
            //TODO add normal exception
            throw new InvalidUserException("dont validate");
        }
        user.setActive(true);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException e){
            result.rejectValue("username", "3", "login is already taken");
            throw new UserExistsException(user.getUsername());
        }

    }
}
