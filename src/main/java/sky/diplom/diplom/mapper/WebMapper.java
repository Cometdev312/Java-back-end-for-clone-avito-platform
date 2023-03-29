package sky.diplom.diplom.mapper;

import java.util.Collection;
import java.util.List;

//  E в D означает преобразование из сущности (Entity) в класс-объект (DTO - Data Transfer Object).
//  Это преобразование происходит, чтобы избежать передачи лишней информации и не нарушать принципы инкапсуляции.
//  Для этого существует специальный интерфейс WebMapper,
//  который предоставляет методы преобразования сущности в класс-объект и наоборот.

public interface WebMapper<D, E> {

    E toEntity(D dto);
    D toDto(E entity);
    List<D> toDto(Collection<E> entity);
    List<E> toEntity(Collection<D> dto);
}