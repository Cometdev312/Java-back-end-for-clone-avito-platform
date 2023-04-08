package sky.diplom.diplom.service.impl;

import liquibase.repackaged.net.sf.jsqlparser.util.validation.ValidationException;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import sky.diplom.diplom.dto.Role;
import sky.diplom.diplom.dto.UserDto;
import sky.diplom.diplom.entity.User;
import sky.diplom.diplom.repository.UserRepository;
import sky.diplom.diplom.security.SecurityUtils;
import sky.diplom.diplom.security.UserDetailsServiceImpl;
import sky.diplom.diplom.service.ImageService;
import sky.diplom.diplom.service.UserService;

import java.time.Instant;

import static sky.diplom.diplom.dto.Role.USER;
import static sky.diplom.diplom.security.SecurityUtils.getUserDetailsFromContext;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public User getUser() {
        return userRepository.findByEmailIgnoreCase(SecurityUtils.
                        getUserDetailsFromContext().getUsername()).
                orElseThrow(() -> new NotFoundException(String.format("User with email \"%s\" not found!",
                        getUserDetailsFromContext().getUsername())));
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
    }

    public User createUser(User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new ValidationException(String.format("User \"%s\" already exists!", user.getEmail()));
        }
        if (user.getRole() == null) {
            user.setRole(USER);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegDate(Instant.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = getUserById(getUserDetailsFromContext().getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        return userRepository.save(user);
    }

    @Override
    public void newPassword(String newPassword, String currentPassword) {
        UserDetails userDetails = getUserDetailsFromContext();
        if (!passwordEncoder.matches(currentPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("The current password is incorrect!");
        }
        userDetailsService.updatePassword(userDetails, passwordEncoder.encode(newPassword));
    }

    @Override
    @SneakyThrows
    public String updateUserImage(MultipartFile image) {
        User user = getUserById(getUserDetailsFromContext().getId());
        imageService.remove(user.getImage());
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
}
