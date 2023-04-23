package kz.comics.account.mapper;

import kz.comics.account.model.comics.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

@Component
@AllArgsConstructor
public class ComicsMapper {

    private final ImageMapper imageMapper;

    public ComicsEntity toEntity(ComicsDto comicsDto) {
        return ComicsEntity.builder()
                .name(comicsDto.getName())
                .author(comicsDto.getAuthor())
                .genres(new LinkedHashSet<>(comicsDto.getGenres()))
                .cover(comicsDto.getCover())
                .chapters(imageMapper.toImageEntityList(comicsDto.getChapters()))
                .likes(comicsDto.getLikes())
                .build();
    }

    public ComicsDto toDto(ComicsEntity comicsEntity) {
        return ComicsDto.builder()
                .name(comicsEntity.getName())
                .author(comicsEntity.getAuthor())
                .genres(comicsEntity.getGenres().stream().toList())
                .cover(comicsEntity.getCover())
                .chapters(imageMapper.toImageDtoList(comicsEntity.getChapters()))
                .likes(comicsEntity.getLikes())
                .build();
    }
}
