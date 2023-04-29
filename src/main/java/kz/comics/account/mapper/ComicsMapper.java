package kz.comics.account.mapper;

import kz.comics.account.model.comics.*;
import kz.comics.account.repository.entities.ComicsEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

@Component
@AllArgsConstructor
public class ComicsMapper {

    private final ChapterMapper chapterMapper;

    public ComicsEntity toEntity(ComicsDto comicsDto) {
        ComicsEntity comicsEntity = ComicsEntity.builder()
                .name(comicsDto.getName())
                .author(comicsDto.getAuthor())
                .genres(new LinkedHashSet<>(comicsDto.getGenres()))
                .cover(comicsDto.getCover())
                .likes(comicsDto.getLikes())
                .build();

        // FIXME: Негизи непонятно зачем я тут сохр если я пересохраняю в сервисе
        comicsEntity.setChapters(chapterMapper.toChapterEntityList(comicsDto.getChapters(), comicsEntity));
        return comicsEntity;
    }

    public ComicsDto toDto(ComicsEntity comicsEntity) {
        return ComicsDto.builder()
                .name(comicsEntity.getName())
                .author(comicsEntity.getAuthor())
                .genres(comicsEntity.getGenres().stream().toList())
                .cover(comicsEntity.getCover())
                .chapters(chapterMapper.toChapterDtoList(comicsEntity.getChapters()))
                .likes(comicsEntity.getLikes())
                .build();
    }
}
