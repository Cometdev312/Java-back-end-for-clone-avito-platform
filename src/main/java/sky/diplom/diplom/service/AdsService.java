package sky.diplom.diplom.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import sky.diplom.diplom.dto.CreateAdsDto;
import sky.diplom.diplom.entity.Ads;

import java.util.Collection;

public interface AdsService {

    /**
     * Находит все экземпляры Ads в хранилище,
     * преобразует их в AdsDto и затем возвращает их с помощью ResponseWrapper
     *
     * @return ResponseWrapper<AdsDto>
     */
    Collection<Ads> getAllAds();

    /**
     * Находит объявления, созданные пользователем,
     * который делает этот запрос.
     *
     * @return ResponseWrapper<AdsDto>
     */
    Collection<Ads> getAdsMe(Authentication authentication);

    /**
     * Находит экземпляр Ads в хранилище по его id и преобразует его в
     * экземпляр FullAdsDto. Цель - предоставить пользователю
     * полную информацию о выбранном объявлении
     *
     * @param adId Ads id
     * @return ResponseEntity<FullAdsDto>
     */
    Ads getAdsById(Long adId);

    /**
     * Создает объявление на основе CreateAdsDto и фотографии
     *
     * @param createAdsDto   Модель объявления Dto с названием, описанием и ценой
     * @param imageFiles     Объявление фотографии
     * @param authentication
     * @return AdsDto
     */
    Ads addAds(CreateAdsDto createAdsDto, MultipartFile imageFiles, Authentication authentication);

    /**
     * Удаление объявления по ID объявления
     *
     * @param adId Ad ID
     * @return ResponseEntity<Void>
     */
    Ads removeAdsById(Long adId, Authentication authentication);

    /**
     * Обновление информации об объявлении
     *
     * @param adId         Ads id
     * @param createAdsDto DTO который содержит необходимые изменения.
     * @return ResponseEntity<AdsDto>
     */
    Ads updateAds(Long adId, CreateAdsDto createAdsDto, Authentication authentication);


    /**
     * Обновляет информацию об экземпляре изображения (поля: fileSize, mediaType, data).
     * Цель - заменить старое изображение для объявления на сайте на новое.
     *
     * @param id    Идентификатор экземпляра изображения
     * @param image image file
     * @return ResponseEntity<byte [ ]>
     */
    void updateAdsImage(long id, MultipartFile image, Authentication authentication);
}
