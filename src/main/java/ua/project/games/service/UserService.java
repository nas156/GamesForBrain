package ua.project.games.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.project.games.entity.User;
import ua.project.games.entity.enums.Role;
import ua.project.games.repository.UserRepository;

/**
 * Клас, в якому описуються методи для роботи з таблицею usr(класом User)
 */
@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Метод, який знаходить в таблиці usr запис, поле username якого співпадає з переданим в параметрах об'єктом типу String
     * Викидає UsernameNotFoundException, якщо запису, поле username якого співпадає з переданою в параметрі строкою немає
     * @param s
     * @return Об'єкт типу User, поле username якого співпадає з переданим в параметрах об'єктом типу String, або Optional.empty()
     * @throws UsernameNotFoundException
     */
    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).get();
    }



    public Role getUserRole(String username) {
        return userRepository.findByUsername(username).orElse(new User()).getRole();
    }
}
