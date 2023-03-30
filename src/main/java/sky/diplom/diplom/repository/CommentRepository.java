package sky.diplom.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.diplom.diplom.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
