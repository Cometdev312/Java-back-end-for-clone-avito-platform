package sky.diplom.diplom.service.impl;

import org.springframework.stereotype.Service;
import sky.diplom.diplom.dto.CommentDto;
import sky.diplom.diplom.repository.AdsRepository;
import sky.diplom.diplom.repository.CommentRepository;
import sky.diplom.diplom.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private AdsRepository adsRepository;

    public CommentServiceImpl(CommentRepository commentRepository, AdsRepository adsRepository) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
    }

    @Override
    public List<CommentDto> getCommentsByAdsId(Long adId) {
        return null;
    }

    @Override
    public CommentDto createComment(Long adId, CommentDto commentDto) {
        return null;
    }

    @Override
    public CommentDto getCommentById(Long adId, Long commentId) {
        return null;
    }

    @Override
    public void deleteComment(Long adId, Long commentId) {

    }

    @Override
    public CommentDto updateComment(Long adId, Long commentId, CommentDto commentDto) {
        return null;
    }
}
