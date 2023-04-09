package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ads;

import java.util.Collection;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {
    Collection<Ads> findAllByAuthorId(long id);
}
