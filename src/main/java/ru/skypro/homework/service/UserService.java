package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {

    /**
     * Get all users from repository
     * and return them all
     *
     * @return Collection<User>
     */

    User getUser(Authentication authentication);

    /**
     * Get user by ID
     *
     * @param id ID user
     */
    User getUserById(long id);

    /**
     * Changes user data
     *
     * @param userDto User object with new data
     * @return User
     */
    User updateUser(UserDto userDto, Authentication authentication);

    /**
     * Changing the user's password
     *
     * @param newPassword     New Password
     * @param currentPassword Current Password
     */
    void updatePassword(String newPassword, String currentPassword, Authentication authentication);

    /**
     * Updates users image
     *
     * @param image new image
     * @return images path
     */
    String updateUserImage(MultipartFile image, Authentication authentication);

    /**
     * Changing a user's role
     *
     * @param id   identifier User
     * @param role New Role
     */
    User updateRole(long id, Role role);
}

