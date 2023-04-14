package ru.skypro.homework.service.impl;

import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdsCommentMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsCommentRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.security.SecurityUtils;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.time.Instant;
import java.util.Collection;

import static ru.skypro.homework.security.SecurityUtils.*;

@Transactional
@Service
public class AdsServiceImpl implements AdsService {

    private final UserService userService;
    private final ImageService imageService;
    private final AdsRepository adsRepository;
    private final AdsCommentRepository adsCommentRepository;
    private final AdsMapper adsMapper;
    private final AdsCommentMapper adsCommentMapper;

    public AdsServiceImpl(UserService userService, ImageService imageService, AdsRepository adsRepository,
                          AdsCommentRepository adsCommentRepository,
                          AdsMapper adsMapper, AdsCommentMapper adsCommentMapper) {
        this.userService = userService;
        this.imageService = imageService;
        this.adsRepository = adsRepository;
        this.adsCommentRepository = adsCommentRepository;
        this.adsMapper = adsMapper;
        this.adsCommentMapper = adsCommentMapper;
    }

    @Override
    public Collection<Ads> getAllAds() {
        return adsRepository.findAll();
    }

    @SneakyThrows
    @Override
    public Ads addAds(CreateAdsDto createAdsDto, MultipartFile imageFile, Authentication authentication) {
        Ads ads = adsMapper.toEntity(createAdsDto);
        User user = userService.getUser(authentication);
        ads.setAuthor(user);
        ads.setImage(imageService.uploadImage(imageFile));
        return adsRepository.save(ads);
    }

    @Override
    public Collection<Ads> getAdsMe(Authentication authentication) {
        return adsRepository.findAllByAuthorId(userService.getUser(authentication).getId());
    }

    public Ads getAdsById(Long adId) {
        return adsRepository.findById(adId).orElseThrow(
                () -> new NotFoundException("Ad with id " + adId + " not found!"));
    }

    public Ads removeAdsById(Long adId, Authentication authentication) {
        Ads ads = getAdsById(adId);
        checkPermissionToAds(ads, userService.getUser(authentication));
        adsCommentRepository.deleteAdsCommentsByAdId(adId);
        adsRepository.delete(ads);
        return ads;
    }

    @Override
    public AdsComment getAdsComment(long adPk, long id) {
        return adsCommentRepository.findByIdAndAdId(id, adPk)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %d " +
                        "belonging to ad with id %d not found!", id, adPk)));
    }

    @Override
    public Collection<AdsComment> getComments(long adPk) {
        return adsCommentRepository.findAllByAdId(adPk);
    }

    @Override
    public AdsComment addAdsComments(long adPk, AdsCommentDto adsCommentDto, Authentication authentication) {
        AdsComment adsComment = adsCommentMapper.toEntity(adsCommentDto);
        User user = userService.getUser(authentication);
        adsComment.setAuthor(user);
        adsComment.setAd(getAdsById(adPk));
        adsComment.setCreatedAt(Instant.now());
        return adsCommentRepository.save(adsComment);
    }

    @Override
    public AdsComment deleteAdsComment(long adPk, long id, Authentication authentication) {
        AdsComment comment = getAdsComment(adPk, id);
        checkPermissionToAdsComment(comment, userService.getUser(authentication));
        adsCommentRepository.delete(comment);
        return comment;
    }

    @Override
    public AdsComment updateComments(long adPk, long id, AdsComment adsCommentUpdated, Authentication authentication) {
        AdsComment adsComment = getAdsComment(adPk, id);
        SecurityUtils.checkPermissionToAdsComment(adsComment, userService.getUser(authentication));
        adsComment.setText(adsCommentUpdated.getText());
        return adsCommentRepository.save(adsComment);
    }

    @Override
    @SneakyThrows
    public void updateAdsImage(long id, MultipartFile image, Authentication authentication) {
        if (image == null) {
            throw new NotFoundException("New ad image not uploaded");
        }
        Ads ads = getAdsById(id);
        checkPermissionToAds(ads, userService.getUser(authentication));
        imageService.remove(ads.getImage());
        ads.setImage(imageService.uploadImage(image));
        adsRepository.save(ads);
    }

    @Override
    public Ads updateAds(Long adId, CreateAdsDto createAdsDto, Authentication authentication) {
        Ads ads = getAdsById(adId);
        checkPermissionToAds(ads, userService.getUser(authentication));
        ads.setTitle(createAdsDto.getTitle());
        ads.setDescription(createAdsDto.getDescription());
        ads.setPrice(createAdsDto.getPrice());
        return adsRepository.save(ads);
    }
}
