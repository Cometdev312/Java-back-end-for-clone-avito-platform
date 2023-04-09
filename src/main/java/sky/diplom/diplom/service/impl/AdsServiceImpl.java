package sky.diplom.diplom.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
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


import java.util.Collection;
import java.util.List;

import static sky.diplom.diplom.security.SecurityUtils.checkPermissionToAdsComment;


public class AdsServiceImpl implements AdsService {

    Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    private final AdsRepository adsRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final ImageService imagesService;

    private final AdsMapper adsMapper;

    private final CommentMapper commentMapper;



    @Override
    public Collection<Ads> getAllAds() {
        return null;
    }

    @Override
    public Collection<Ads> getAdsMe(Authentication authentication) {
        return null;
    }

    @Override
    public Ads getAdsById(Long adId) {
        return null;
    }

    @Override
    public Ads addAds(CreateAdsDto createAdsDto, MultipartFile imageFiles, Authentication authentication) {
        return null;
    }

    @Override
    public Ads removeAdsById(Long adId, Authentication authentication) {
        return null;
    }

    @Override
    public Ads updateAds(Long adId, CreateAdsDto createAdsDto, Authentication authentication) {
        return null;
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
