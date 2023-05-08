/*
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
    private final ImageCoverMapper imageCoverMapper;

    public ComicsEntity toEntity(ComicsDto comicsDto) {
        ComicsEntity comicsEntity = ComicsEntity.builder()
                .name(comicsDto.getName())
                .author(comicsDto.getAuthor())
                .genres(new LinkedHashSet<>(comicsDto.getGenres()))
                .cover(imageCoverMapper.toEntity(comicsDto.getCover()))
                .rating(comicsDto.getRating())
                .rates(comicsDto.getRates())
                .description(comicsDto.getDescription())
                .type(comicsDto.getType())
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
                .cover(imageCoverMapper.toDto(comicsEntity.getCover()))
                .chapters(chapterMapper.toChapterDtoList(comicsEntity.getChapters()))
                .rating(comicsEntity.getRating())
                .rates(comicsEntity.getRates())
                .description(comicsEntity.getDescription())
                .type(comicsEntity.getType())
                .build();
    }
}
*/
