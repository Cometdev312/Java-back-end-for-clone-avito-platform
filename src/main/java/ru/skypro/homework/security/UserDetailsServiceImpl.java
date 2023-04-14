package ru.skypro.homework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

import javax.transaction.Transactional;

@Transactional
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new MyUserDetails(user);
    }

    private User getUserByUsername(String username) {
        return userRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Пользователь с email: \"%s\" не найден", username)));
    }
}
