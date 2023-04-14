package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserDetailsServiceImpl;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public User getUser(Authentication authentication) {
        return getUserByUsername(authentication.getName());
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
    }

    @Override
    public User updateUser(UserDto userDto, Authentication authentication) {
        User user = getUserByUsername(authentication.getName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(String newPassword, String currentPassword, Authentication authentication) {
        User user = getUserByUsername(authentication.getName());
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("The current password is incorrect!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @SneakyThrows
    public String updateUserImage(MultipartFile image, Authentication authentication) {
        User user = getUserByUsername(authentication.getName());
        if (user.getImage() != null) {
            imageService.remove(user.getImage());
        }
        user.setImage(imageService.uploadImage(image));
        return "/users/image/" + userRepository.save(user).getImage().getId();
    }

    @Override
    public User updateRole(long id, Role role) {
        User user = getUserById(id);
        user.setRole(role);
        userRepository.save(user);
        return user;
    }

    private User getUserByUsername(String username) {
        return userRepository.findByEmailIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("The user with email: \"%s\" not found", username)));
    }
}
