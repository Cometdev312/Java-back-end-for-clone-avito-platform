package sky.diplom.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sky.diplom.diplom.dto.CommentDto;
import sky.diplom.diplom.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    //Данный метод преобразовывает сущность Comment в обект CommentDto.
    //DateFormat помогает нам уточнить, в каком формате будет преобразованна строка.
    //Пример в схеме: дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970.
    @Mapping(target = "pk", source = "comment.pk")
    @Mapping(target = "author", source = "comment.author.id")
    @Mapping(target = "authorImage", source = "comment.authorImage")
    @Mapping(target = "authorFirstName", source = "comment.authorFirstName.name")
    @Mapping(target = "createdAt", source = "comment.createdAt", dateFormat = "HH:mm:ss dd-MM-yyyy")
    @Mapping(target = "text", source = "comment.text")
    CommentDto toDto(Comment comment);

    //Данный метод преобразовывает обект CommentDto в сущность Comment
    @Mapping(target = "pk", source = "commentDto.pk")
    @Mapping(target = "author", source = "commentDto.author")
    @Mapping(target = "authorImage", source = "commentDto.authorImage")
    @Mapping(target = "authorFirstName", source = "commentDto.authorFirstName")
    @Mapping(target = "createdAt", source = "commentDto.createdAt", dateFormat = "HH:mm:ss dd-MM-yyyy")
    @Mapping(target = "text", source = "commentDto.text")
    Comment toEntity(Comment comment);

}
