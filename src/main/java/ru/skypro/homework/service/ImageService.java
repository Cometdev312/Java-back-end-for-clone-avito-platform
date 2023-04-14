package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;

import java.io.IOException;

public interface ImageService {

    /**
     * Saving an image to the database
     *
     * @param imageFile Object picture
     * @return Images
     */
    Image uploadImage(MultipartFile imageFile) throws IOException;

    /**
     * Getting a picture by ID
     *
     * @param id Id Pictures
     * @return Images
     */
    Image getImageById(long id);

    /**
     * Delete image by ID
     *
     * @param image image to delete
     */
    void remove(Image image);

    /**
     * Updates Image instance`s info (fields: fileSize, mediaType, data).
     * The purpose is to replace an old image for an ad on the website with a new one.
     *
     * @param id    Image instance`s id
     * @param image image file
     * @return ResponseEntity<byte [ ]>
     */

}
