package sky.diplom.diplom.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import sky.diplom.diplom.dto.CreateAdsDto;
import sky.diplom.diplom.entity.Ads;
import sky.diplom.diplom.service.AdsService;


import java.util.Collection;

public class AdsServiceImpl implements AdsService {


    @Override
    public Collection<Ads> getAllAds() {
        return null;
    }

    @Override
    public Collection<Ads> getAdsMe(Authentication authentication) {
        return null;
    }

    @Override
    public Ads getAdsById(Long adId) {
        return null;
    }

    @Override
    public Ads addAds(CreateAdsDto createAdsDto, MultipartFile imageFiles, Authentication authentication) {
        return null;
    }

    @Override
    public Ads removeAdsById(Long adId, Authentication authentication) {
        return null;
    }

    @Override
    public Ads updateAds(Long adId, CreateAdsDto createAdsDto, Authentication authentication) {
        return null;
    }

    @Override
    public void updateAdsImage(long id, MultipartFile image, Authentication authentication) {

    }
}
