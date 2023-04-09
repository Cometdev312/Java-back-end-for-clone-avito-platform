package sky.diplom.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.diplom.diplom.entity.Comment;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndAdId(long id, long adsId);

    Collection<Comment> findAllByAdId(long adId);

    void deleteAdsCommentsByAdId(long id);
}