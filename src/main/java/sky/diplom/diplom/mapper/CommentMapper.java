package sky.diplom.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sky.diplom.diplom.dto.CommentDto;
import sky.diplom.diplom.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper extends WebMapper<CommentDto, Comment>{

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    //Данный метод преобразовывает сущность Comment в обект CommentDto.
    //DateFormat помогает нам уточнить, в каком формате будет преобразованна строка.
    //Пример в схеме: дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970.
    @Mapping(source = "pk", target = "id")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "HH:mm:ss dd-MM-yyyy")
    CommentDto toDto(Comment comment);

    //Данный метод преобразовывает обект CommentDto в сущность Comment
    @Mapping(source = "id", target = "pk")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "HH:mm:ss dd-MM-yyyy")
    Comment toEntity(Comment comment);

}
