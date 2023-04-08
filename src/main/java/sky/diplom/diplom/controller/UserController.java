package sky.diplom.diplom.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.diplom.diplom.dto.CreateUserDto;
import sky.diplom.diplom.dto.NewPasswordDto;
import sky.diplom.diplom.dto.Role;
import sky.diplom.diplom.dto.UserDto;
import sky.diplom.diplom.entity.User;
import sky.diplom.diplom.mapper.UserMapper;
import sky.diplom.diplom.service.ImageService;
import sky.diplom.diplom.service.UserService;

import javax.validation.Valid;

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

    @Operation(summary = "addUser", description = "addUser")
    @PostMapping
    public ResponseEntity<CreateUserDto> addUser(@Valid @RequestBody CreateUserDto createUserDto) {
        printLogInfo("addUser", "post", "/users");
        User user = userService.createUser(userMapper.createUserDtoToEntity(createUserDto));
        return ResponseEntity.ok(userMapper.toCreateUserDto(user));
    }

    @Operation(summary = "updateUser", description = "updateUser")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        printLogInfo("updateUser", "patch", "/me");
        return ResponseEntity.ok(userMapper.toDto(userService.updateUser(userDto)));
    }

    @Operation(summary = "setPassword", description = "setPassword")
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPasswordDto) {
        userService.newPassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword());
        printLogInfo("setPassword", "post", "/set_password");
        return ResponseEntity.ok(newPasswordDto);
    }

    @Operation(summary = "updateUserImage", description = "updateUserImage")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@RequestBody MultipartFile image) {
        printLogInfo("updateUserImage", "patch", "/me/image");
        return ResponseEntity.ok().body(userService.updateUserImage(image));
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
    public UserDto getUser() {
        printLogInfo("getUser", "get", "/me");
        return userMapper.toDto((userService.getUser()));
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") int id) {
        printLogInfo("getImageOfUser", "get", "/image/{id}");
        return ResponseEntity.ok(imageService.getImageById(id).getData());
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
