package sky.diplom.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.diplom.diplom.entity.Ads;

public interface AdsRepository extends JpaRepository <Ads, Long> {
}
