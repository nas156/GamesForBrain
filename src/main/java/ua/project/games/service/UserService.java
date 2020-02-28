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

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


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

    public User getById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void delete(@NonNull User user) {
        userRepository.delete(user);
    }

    public Optional<List<User>> findByUsername(String username) {
        return userRepository.findAllByUsername(username);
    }
}
