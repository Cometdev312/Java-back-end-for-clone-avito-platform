package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;

import java.util.Collection;

public interface AdsService {

    /**
     * Finds all Ads instances in the repository, converts them to AdsDto
     * and then returns them using the ResponseWrapper
     *
     * @return ResponseWrapper<AdsDto>
     */
    Collection<Ads> getAllAds();

    /**
     * Finds ads created by the user who makes this request.
     *
     * @return ResponseWrapper<AdsDto>
     */
    Collection<Ads> getAdsMe(Authentication authentication);

    /**
     * Finds an Ads instance in the repository by its id and converts it into
     * a FullAdsDto instance. The purpose is to provide the user with the
     * full information on the chosen advertisement.
     *
     * @param adId Ads id
     * @return ResponseEntity<FullAdsDto>
     */
    Ads getAdsById(Long adId);

    /**
     * Creates an ad based on CreateAdsDto and an photo
     *
     * @param createAdsDto   Ad model Dto with title, description and price
     * @param imageFiles     Ad photos
     * @param authentication
     * @return AdsDto
     */
    Ads addAds(CreateAdsDto createAdsDto, MultipartFile imageFiles, Authentication authentication);

    /**
     * Deletes ad by ad ID
     *
     * @param adId Ad ID
     * @return ResponseEntity<Void>
     */
    Ads removeAdsById(Long adId, Authentication authentication);

    /**
     * Getting a comment by ID
     *
     * @param id   Comment ID
     * @param adPk Ad ID
     * @return Found comment
     */
    AdsComment getAdsComment(long adPk, long id);

    /**
     * Getting all comments by ad ID
     *
     * @param adPk Ad ID
     * @return Collection<AdsComment>
     */
    Collection<AdsComment> getComments(long adPk);

    /**
     * Adds a comment to an ad
     *
     * @param adPk           Ad ID
     * @param adsCommentDto  Comment model Dto with author, createdAt, text
     * @param authentication
     * @return AdsCommentDto
     */
    AdsComment addAdsComments(long adPk, AdsCommentDto adsCommentDto, Authentication authentication);

    /**
     * Deletes a comment
     *
     * @param adPk Ads id
     * @param id   AdsComment id
     * @return ResponseEntity<HttpStatus>
     */
    AdsComment deleteAdsComment(long adPk, long id, Authentication authentication);

    /**
     * Updates an ad`s info
     *
     * @param adId         Ads id
     * @param createAdsDto DTO carrying changes
     * @return ResponseEntity<AdsDto>
     */
    Ads updateAds(Long adId, CreateAdsDto createAdsDto, Authentication authentication);

    /**
     * Changing the comment by ID
     *
     * @param id         Comment id
     * @param adPk       Ad id
     * @param adsComment Modified comment
     * @return Modified comment
     */
    AdsComment updateComments(long adPk, long id, AdsComment adsComment, Authentication authentication);

    /**
     * Updates Image instance`s info (fields: fileSize, mediaType, data).
     * The purpose is to replace an old image for an ad on the website with a new one.
     *
     * @param id    Image instance`s id
     * @param image image file
     * @return ResponseEntity<byte [ ]>
     */
    void updateAdsImage(long id, MultipartFile image, Authentication authentication);
}
