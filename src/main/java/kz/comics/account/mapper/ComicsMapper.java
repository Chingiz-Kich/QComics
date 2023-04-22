package kz.comics.account.mapper;

import kz.comics.account.model.comics.ComicsDto;
import kz.comics.account.model.comics.ComicsEntity;
import kz.comics.account.model.comics.Genre;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ComicsMapper {

    public ComicsEntity toEntity(ComicsDto comicsDto) {
        return ComicsEntity.builder()
                .name(comicsDto.getName())
                .author(comicsDto.getAuthor())
                .genres((Set<Genre>) comicsDto.getGenres())
                .cover(comicsDto.getCover())
                .chapters(comicsDto.getChapters())
                .likes(comicsDto.getLikes())
                .build();
    }

    public ComicsDto toDto(ComicsEntity comicsEntity) {
        return ComicsDto.builder()
                .name(comicsEntity.getName())
                .author(comicsEntity.getAuthor())
                .genres(comicsEntity.getGenres().stream().toList())
                .cover(comicsEntity.getCover())
                .chapters(comicsEntity.getChapters())
                .likes(comicsEntity.getLikes())
                .build();
    }
}
