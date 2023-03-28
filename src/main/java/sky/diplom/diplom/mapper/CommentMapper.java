package sky.diplom.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sky.diplom.diplom.dto.CommentDto;
import sky.diplom.diplom.entity.Comment;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto commentToDto(Comment comment);

    Comment commentToEntity(Comment comment);

    //В данном маппере аннотации @Mapping не нужны,
    //так как поля классов CommentDto и Comment полностью идентичны!
}
