package sky.diplom.diplom.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import sky.diplom.diplom.dto.CommentDto;
import sky.diplom.diplom.dto.CreateAdsDto;
import sky.diplom.diplom.entity.Ads;
import sky.diplom.diplom.entity.Comment;

import java.util.Collection;
import java.util.List;

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
     *
     */
    void updateAdsImage(long id, MultipartFile image, Authentication authentication);

    Comment getComment(long adPk, long id);

    Collection<Comment> getComments(long adPk);

    Comment addComments(long adPk, CommentDto adsCommentDto, Authentication authentication);

    Comment deleteComment(long adPk, long id, Authentication authentication);

    Comment updateComments(long adPk, long id, Comment adsComment, Authentication authentication);

}