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
 * Сервісний клас, для описання логіки, яка пов'язана з реєстрацією користувачів, створенням нових об'єктів класу User та запису їх в базу даних
 * @see Service
 */
@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param userRepository об'єкт репозиторію UserRepository, який містить методи для роботи з таблицею usr
     * @param passwordEncoder об'єк класу BCryptPasswordEncoder, який містить методи для кодування даних
     * @see BCryptPasswordEncoder
     * @see UserRepository
     * @see Autowired
     */
    @Autowired
    public RegistrationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Метод, який записує унікального юзера в таблицю usr
     * @param user об'єкт типу User, який потрібно занести в базу
     * @param result об'єкт класу BindingResult, в який запишеться результат виконання методу
     * @throws InvalidUserException якщо переданий в параметрах клас User не проходить валідування в методі validate() класу NewUserValidator
     * @throws UserExistsException якщо у переданого в пераметрах класу User юзернейм збігається з одним з вже записаних в базу
     * @see User
     * @see BindingResult
     * @see InvalidUserException
     * @see UserExistsException
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
