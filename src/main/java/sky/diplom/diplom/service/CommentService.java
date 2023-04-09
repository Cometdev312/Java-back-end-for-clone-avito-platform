package sky.diplom.diplom.service;

import sky.diplom.diplom.dto.AdsCommentDto;

import java.util.*;

public interface CommentService {

    List<AdsCommentDto> getCommentsByAdsId(Long adId);
    AdsCommentDto createComment(Long adId, AdsCommentDto commentDto);
    AdsCommentDto getCommentById(Long adId, Long commentId);
    void deleteComment(Long adId, Long commentId);
    AdsCommentDto updateComment(Long adId, Long commentId, AdsCommentDto commentDto);
}
