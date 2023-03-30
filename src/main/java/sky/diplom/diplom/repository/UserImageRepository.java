package sky.diplom.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.diplom.diplom.entity.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
}
