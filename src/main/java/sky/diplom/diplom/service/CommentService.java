package sky.diplom.diplom.service;

import sky.diplom.diplom.dto.CommentDto;

import java.util.*;

public interface CommentService {

    List<CommentDto> getCommentsByAdsId(Long adId);
    CommentDto createComment(Long adId, CommentDto commentDto);
    CommentDto getCommentById(Long adId, Long commentId);
    void deleteComment(Long adId, Long commentId);
    CommentDto updateComment(Long adId, Long commentId, CommentDto commentDto);
}
