package sky.diplom.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import sky.diplom.diplom.entity.Image;
import sky.diplom.diplom.repository.ImageRepository;
import sky.diplom.diplom.service.ImageService;

import javax.transaction.Transactional;
import java.io.IOException;

@Transactional
@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public Image uploadImage(MultipartFile imageFile) throws IOException {
        Image image = new Image();
        image.setData(imageFile.getBytes());
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setData(imageFile.getBytes());
        return imageRepository.save(image);
    }

    @Override
    public Image getImageById(long id) {
        return imageRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Image with id " + id + " not found!"));
    }

    @Override
    public void remove(Image image) {
        imageRepository.delete(image);
    }


}
