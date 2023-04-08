package sky.diplom.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sky.diplom.diplom.dto.RegisterReqDto;
import sky.diplom.diplom.dto.Role;
import sky.diplom.diplom.entity.User;
import sky.diplom.diplom.mapper.UserMapper;
import sky.diplom.diplom.repository.UserRepository;
import sky.diplom.diplom.security.UserDetailsServiceImpl;
import sky.diplom.diplom.service.AuthService;

import javax.validation.ValidationException;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean login(String userName, String password) {
        try {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
            String encryptedPassword = userDetails.getPassword();
            return encoder.matches(password, encryptedPassword);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException(String.format("User \"%s\" does not exist!", userName));
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Wrong password!");
        }
    }

    @Override
    public boolean register(RegisterReqDto registerReqDto, Role role) {
        User user = userMapper.toEntity(registerReqDto);
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new ValidationException(String.format("User \"%s\" is already registered!", user.getEmail()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRegDate(Instant.now());
        userRepository.save(user);
        return true;
    }
}