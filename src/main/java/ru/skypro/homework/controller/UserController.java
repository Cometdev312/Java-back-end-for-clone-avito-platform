package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.entity.User;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "UserController")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserMapper userMapper;
    private final ImageService imageService;

    @Operation(summary = "updateUser", description = "updateUser")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, Authentication authentication) {
        printLogInfo("updateUser", "patch", "/me");
        return ResponseEntity.ok(userMapper.toDto(userService.updateUser(userDto, authentication)));
    }

    @Operation(summary = "setPassword", description = "setPassword")
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPasswordDto, Authentication authentication) {
        userService.updatePassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword(), authentication);
        printLogInfo("setPassword", "post", "/set_password");
        return ResponseEntity.ok(newPasswordDto);
    }

    @Operation(summary = "updateUserImage", description = "updateUserImage")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@RequestBody MultipartFile image, Authentication authentication) {
        printLogInfo("updateUserImage", "patch", "/me/image");
        return ResponseEntity.ok().body(userService.updateUserImage(image, authentication));
    }

    @Operation(summary = "getUserById", description = "getUserById")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        printLogInfo("getUserById", "get", "/id");
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @Operation(summary = "getUser", description = "Get info about me")
    @GetMapping("/me")
    public UserDto getUser(Authentication authentication) {
        printLogInfo("getUser", "get", "/me");
        return userMapper.toDto((userService.getUser(authentication)));
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") int id, Authentication authentication) {
        printLogInfo("getImageOfUser", "get", "/image/{id}");
        return ResponseEntity.ok(imageService.getImageById(userService.getUser(authentication).getImage().getId()).getData());
    }

    @Operation(summary = "updateRole", description = "updateRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/updateRole")
    public ResponseEntity<UserDto> updateRole(@PathVariable("id") long id, Role role) {
        printLogInfo("updateAdsImage", "patch", "/id");
        UserDto userDto = userMapper.toDto(userService.updateRole(id, role));
        return ResponseEntity.ok(userDto);
    }

    private void printLogInfo(String name, String requestMethod, String path) {
        logger.info("Вызван метод " + name + ", адрес "
                + requestMethod.toUpperCase() + " запроса /users" + path);
    }
}
