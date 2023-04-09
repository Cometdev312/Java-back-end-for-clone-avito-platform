package sky.diplom.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sky.diplom.diplom.dto.AdsCommentDto;
import sky.diplom.diplom.entity.AdsComment;

@Mapper(componentModel = "spring")
public interface AdsCommentMapper extends WebMapper<AdsCommentDto, AdsComment>{

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", source = "pk")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ad", ignore = true)
    AdsComment toEntity(AdsCommentDto dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", source = "createdAt")
    AdsCommentDto toDto(AdsComment entity);
}
