package ua.project.games.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.project.games.entity.User;
import ua.project.games.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Клас, в якому описуються методи для роботи з таблицею usr(класом User)
 */
@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Конструктор для классу з підтримкою підставлення залежностей за допомогою Spring framework
     * @param userRepository об'єкт репозиторію UserRepository, який містить методи для роботи з таблицею usr
     * @see UserRepository
     * @see Autowired
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод, який знаходить в таблиці usr запис, поле username якого співпадає з переданим в параметрах об'єктом типу String
     * @param s ім'я юзера, якого потрібно знайти
     * @return Об'єкт типу User, поле username якого співпадає з переданим в параметрах об'єктом типу String, або Optional.empty()
     * @throws UsernameNotFoundException якщо запису, поле username якого співпадає з переданою в параметрі, строкою немає
     * @see User
     * @see UsernameNotFoundException
     */
    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElse(null);
    }


    public void updateUser(@NonNull User userToUpdate, @NonNull User dataUser){
        userToUpdate.setUsername(dataUser.getUsername());
        userToUpdate.setActive(dataUser.isActive());
        userToUpdate.setEmail(dataUser.getEmail());
        userToUpdate.setPassword(dataUser.getPassword());
        userToUpdate.setRole(dataUser.getRole());
        userToUpdate.setAge(dataUser.getAge());
        userRepository.save(userToUpdate);
    }

    public Optional<User> getById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user;

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void delete(@NonNull User user) {
        userRepository.delete(user);
    }

    public Optional<List<User>> findByUsername(String username) {
        return userRepository.findAllByUsernameContaining(username);
    }
}
