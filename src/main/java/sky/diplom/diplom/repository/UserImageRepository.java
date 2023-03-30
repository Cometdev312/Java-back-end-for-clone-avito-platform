package sky.diplom.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.diplom.diplom.entity.UserImage;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {
}
