package sky.diplom.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.diplom.diplom.entity.Ads;

import java.util.Collection;

public interface AdsRepository extends JpaRepository <Ads, Long> {
    Collection<Ads> findAllByAuthorId(long id);
}
