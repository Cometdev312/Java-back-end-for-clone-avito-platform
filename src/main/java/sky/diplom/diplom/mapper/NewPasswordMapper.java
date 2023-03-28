package sky.diplom.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sky.diplom.diplom.dto.NewPasswordDto;
import sky.diplom.diplom.entity.User;

@Mapper
public interface NewPasswordMapper {
    NewPasswordMapper INSTANCE = Mappers.getMapper(NewPasswordMapper.class);

    @Mapping(target = "newPassword", source = "password")
    NewPasswordDto userToNewPasswordDto(User user);
}

