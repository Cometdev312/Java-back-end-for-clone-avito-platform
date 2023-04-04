package sky.diplom.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sky.diplom.diplom.dto.NewPasswordDto;
import sky.diplom.diplom.dto.RegisterReqDto;
import sky.diplom.diplom.dto.UpdateUserDto;
import sky.diplom.diplom.entity.User;
import sky.diplom.diplom.mapper.UpdateUserMapper;
import sky.diplom.diplom.repository.UserRepository;
import sky.diplom.diplom.service.UserService;

import java.awt.*;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;

    @Override
    public NewPasswordDto updatePassword(NewPasswordDto newPasswordDto) {
        return null;
    }

    @Override
    public User updateUser(UpdateUserDto userDto) {
        return null;
    }

    @Override
    public ResponseEntity<User> getUser() {

        return null;
    }



    @Override
    public User createUser(RegisterReqDto registerReqDto) {
        return null;
    }

    @Override
    public Image updateUserImage(Image image) {
        return null;
    }


    private Optional<User> getUserByEmail(String email) {
        return repository.findByEmailIgnoreCase(email);
    }

}
