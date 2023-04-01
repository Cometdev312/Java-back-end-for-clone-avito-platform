package sky.diplom.diplom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sky.diplom.diplom.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

}
