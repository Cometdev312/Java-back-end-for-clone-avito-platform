package sky.diplom.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import sky.diplom.diplom.dto.NewPasswordDto;
import sky.diplom.diplom.dto.RegisterReqDto;
import sky.diplom.diplom.dto.UpdateUserDto;
import sky.diplom.diplom.entity.User;
import sky.diplom.diplom.entity.UserImage;
import sky.diplom.diplom.repository.UserRepository;
import sky.diplom.diplom.service.ImageService;
import sky.diplom.diplom.service.UserService;

import java.awt.*;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final ImageService imageService;

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
    public Image updateUserImage(UserImage image) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
    }


    private Optional<User> getUserByEmail(String email) {
        return repository.findByEmailIgnoreCase(email);
    }


}
