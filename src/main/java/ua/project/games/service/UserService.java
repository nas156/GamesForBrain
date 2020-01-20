package ua.project.games.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import ua.project.games.entity.enums.Role;
import ua.project.games.entity.User;
import ua.project.games.repository.UserRepository;

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
        return userRepository.findByUsername(s);
    }
}
