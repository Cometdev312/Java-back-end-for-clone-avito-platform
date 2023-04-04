package sky.diplom.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sky.diplom.diplom.dto.UpdateUserDto;
import sky.diplom.diplom.entity.User;

@Mapper
public interface UpdateUserMapper {
    UpdateUserMapper INSTANCE = Mappers.getMapper(UpdateUserMapper.class);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "image", ignore = true)
    UpdateUserDto toUser(User user);

}
