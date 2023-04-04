package sky.diplom.diplom.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.diplom.diplom.dto.LoginReqDto;
import sky.diplom.diplom.dto.NewPasswordDto;
import sky.diplom.diplom.dto.RegisterReqDto;
import sky.diplom.diplom.service.UserService;

import java.awt.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {
    private UserService userService;

    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPasswordDto) {
        return null;
    }

    @GetMapping("/me")
    public ResponseEntity<LoginReqDto> login() {
        return null;
    }

    @PatchMapping("/me")
    public ResponseEntity<RegisterReqDto> updateUser(@RequestBody RegisterReqDto registerReqDto) {
        return null;
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Image> updateImage(@RequestBody Image image) {
        return null;
    }
}
