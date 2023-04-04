package sky.diplom.diplom.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import sky.diplom.diplom.dto.NewPasswordDto;
import sky.diplom.diplom.dto.RegisterReqDto;
import sky.diplom.diplom.dto.UpdateUserDto;
import sky.diplom.diplom.entity.User;

import java.awt.*;

public interface UserService {
    NewPasswordDto updatePassword(NewPasswordDto newPasswordDto);

    User updateUser(UpdateUserDto userDto);

    ResponseEntity<User> getUser();

    User createUser(RegisterReqDto registerReqDto);

    Image updateUserImage(Image image);
}
