package sky.diplom.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sky.diplom.diplom.dto.AdsDto;
import sky.diplom.diplom.dto.CreateAdsDto;
import sky.diplom.diplom.dto.FullAdsDto;
import sky.diplom.diplom.entity.Ads;
import sky.diplom.diplom.entity.Image;

@Mapper(componentModel = "spring")
public interface AdsMapper extends WebMapper<AdsDto, Ads> {

    //Константа, которая используется для определения пути к изображениям объявлений.
    String ADS_IMAGES = "/ads/image/";

    // Этот метод преобразует AdsDto в Ads
    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "image", ignore = true)
    Ads toEntity(AdsDto dto);

    // Этот метод преобразует Ads в AdsDto
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    AdsDto toDto(Ads entity);

    // Этот метод преобразует CreateAdsDto в Ads
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ads toEntity(CreateAdsDto dto);

    // Этот метод преобразует Ads в FullAdsDto
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    @Mapping(target = "pk", source = "id")
    FullAdsDto toFullAdsDto(Ads entity);

    // Этот метод преобразует Image в строку
    @Named("imageMapping")
    default String imageMapping(Image userImage) {
        if (userImage == null) {
            return null;
        }
        return ADS_IMAGES + userImage.getId();
    }
}

