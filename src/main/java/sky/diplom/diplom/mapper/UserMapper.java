package sky.diplom.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sky.diplom.diplom.dto.CreateUserDto;
import sky.diplom.diplom.dto.RegisterReqDto;
import sky.diplom.diplom.dto.UserDto;
import sky.diplom.diplom.entity.Image;
import sky.diplom.diplom.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends WebMapper<UserDto, User> {

    String ADS_IMAGES = "/users/image/";

    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(source = "username", target = "email")
    User toEntity(RegisterReqDto dto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto dto);

    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    UserDto toDto(User entity);

    @Named("imageMapping")
    default String imageMapping(Image image) {
        if (image == null) {
            return null;
        }
        return ADS_IMAGES + image.getId();
    }
}
