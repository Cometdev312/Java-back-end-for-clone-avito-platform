package sky.diplom.diplom.service;

import org.springframework.web.multipart.MultipartFile;
import sky.diplom.diplom.entity.UserImage;

import java.io.IOException;

public interface ImageService {
    UserImage upload(MultipartFile imageFile) throws IOException;

    UserImage getImageById(Long id);

    void remove(UserImage image);
}
