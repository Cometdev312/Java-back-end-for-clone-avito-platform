package sky.diplom.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sky.diplom.diplom.dto.LoginReqDto;
import sky.diplom.diplom.entity.User;
@Mapper
public interface LoginReqMapper {
    LoginReqMapper INSTANCE = Mappers.getMapper(LoginReqMapper.class);

    @Mapping(target = "password", source = "password")
    @Mapping(target = "username", source = "username")
    LoginReqDto login(User user);
}
