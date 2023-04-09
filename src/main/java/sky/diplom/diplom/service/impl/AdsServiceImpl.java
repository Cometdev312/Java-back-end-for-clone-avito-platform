package sky.diplom.diplom.service.impl;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import sky.diplom.diplom.dto.CommentDto;
import sky.diplom.diplom.dto.CreateAdsDto;
import sky.diplom.diplom.entity.Ads;
import sky.diplom.diplom.entity.Comment;
import sky.diplom.diplom.entity.User;
import sky.diplom.diplom.mapper.AdsMapper;
import sky.diplom.diplom.mapper.CommentMapper;
import sky.diplom.diplom.repository.AdsRepository;
import sky.diplom.diplom.repository.CommentRepository;
import sky.diplom.diplom.repository.UserRepository;
import sky.diplom.diplom.security.SecurityUtils;
import sky.diplom.diplom.service.AdsService;
import sky.diplom.diplom.service.ImageService;
import sky.diplom.diplom.service.UserService;



import java.util.Collection;
import java.util.List;

import static sky.diplom.diplom.security.SecurityUtils.checkPermissionToAds;
import static sky.diplom.diplom.security.SecurityUtils.checkPermissionToAdsComment;

@Transactional
@Service
public class AdsServiceImpl implements AdsService {

    private final UserService userService;
    private final ImageService imageService;
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final AdsMapper adsMapper;
    private final CommentMapper сommentMapper;

    public AdsServiceImpl(UserService userService, ImageService imageService, AdsRepository adsRepository,
                          CommentRepository commentRepository,
                          AdsMapper adsMapper, CommentMapper сommentMapper) {
        this.userService = userService;
        this.imageService = imageService;
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.adsMapper = adsMapper;
        this.сommentMapper = сommentMapper;
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
        CommentRepository.deleteAdsCommentsByAdId(adId);
        adsRepository.delete(ads);
        return ads;
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
    public Comment getComment(long adPk, long id) {
        return commentRepository.findByIdAndAdId(id, adPk)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %d " +
                        "belonging to ad with id %d not found!", id, adPk)));
    }

    @Override
    public Collection<Comment> getComments(long adPk) {
        return commentRepository.findAllByAdId(adPk);
    }

    @Override
    public Comment addComments(long adPk, CommentDto commentDto, Authentication authentication) {
        Comment comment = commentMapper.toEntity(commentDto);
        User user = userService.getUser(authentication);
        comment.setAuthor(user);
        comment.setAd(getAdsById(adPk));
        comment.setCreatedAt(Instant.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteComment(long adPk, long id, Authentication authentication) {
        Comment comment = getComment(adPk, id);
        checkPermissionToAdsComment(comment, userService.getUser(authentication));
        commentRepository.delete(comment);
        return comment;
    }

    @Override
    public Comment updateComments(long adPk, long id, Comment comment, Authentication authentication) {
        Comment adsComment = getComment(adPk, id);
        SecurityUtils.checkPermissionToAdsComment(adsComment, userService.getUser(authentication));
        adsComment.setText(commentUpdated.getText());
        return commentRepository.save(adsComment);
    }
}
