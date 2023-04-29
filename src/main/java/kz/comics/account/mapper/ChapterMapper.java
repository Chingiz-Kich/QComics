package kz.comics.account.mapper;

import kz.comics.account.model.comics.ChapterDto;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ComicsEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ChapterMapper {

    private final ImageMapper imageMapper;

    public ChapterDto toDto(ChapterEntity chapterEntity) {
        return ChapterDto.builder()
                .name(chapterEntity.getName())
                .images(imageMapper.toImageDtoList(chapterEntity.getImages()))
                .build();
    }

    public ChapterEntity toEntity(ChapterDto chapterDto, ComicsEntity comicsEntity) {
        return ChapterEntity.builder()
                .name(chapterDto.getName())
                .images(imageMapper.toImageEntityList(chapterDto.getImages()))
                .comics(comicsEntity)
                .build();
    }

    public List<ChapterDto> toChapterDtoList(List<ChapterEntity> chapterEntityList) {
        return chapterEntityList
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<ChapterEntity> toChapterEntityList(List<ChapterDto> chapterDtoList, ComicsEntity comicsEntity) {
        return chapterDtoList
                .stream()
                .map(chapterDto -> this.toEntity(chapterDto, comicsEntity))
                .toList();
    }
}
