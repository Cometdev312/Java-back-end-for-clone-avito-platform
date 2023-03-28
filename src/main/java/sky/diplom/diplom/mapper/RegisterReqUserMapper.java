package sky.diplom.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sky.diplom.diplom.dto.RegisterReqDto;
import sky.diplom.diplom.entity.User;

@Mapper
public interface RegisterReqUserMapper {
    RegisterReqUserMapper INSTANCE = Mappers.getMapper(RegisterReqUserMapper.class);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "phone", source = "phone")
    RegisterReqDto toEntity(User user);

}
