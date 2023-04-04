package sky.diplom.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import sky.diplom.diplom.entity.UserImage;
import sky.diplom.diplom.repository.UserImageRepository;
import sky.diplom.diplom.service.ImageService;

import javax.transaction.Transactional;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    private final UserImageRepository imageRepository;

    @Override
    public UserImage upload(MultipartFile imageFile) throws IOException {
        UserImage image = new UserImage();
        image.setMediaType(imageFile.getContentType());
        image.setImage(imageFile.getBytes());
        image.setTitle(imageFile.getName());
        image.setFileSize(imageFile.getSize());
        return imageRepository.save(image);
    }

    @Override
    public UserImage getImageById(Long id) {
       return imageRepository.findById(id).orElseThrow(()->
               new NotFoundException("Image with id " + id + " not found"));
    }

    @Override
    public void remove(UserImage image) {
        imageRepository.delete(image);
    }
}
