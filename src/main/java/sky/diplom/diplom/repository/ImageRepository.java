package sky.diplom.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.diplom.diplom.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
