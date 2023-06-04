package kz.comics.account.mapper;

import kz.comics.account.model.comics.*;
import kz.comics.account.repository.entities.ComicsEntity;
import lombok.AllArgsConstructor;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.stereotype.Component;

 import java.util.Base64;
 import java.util.LinkedHashSet;

@Component
@AllArgsConstructor
public class ComicsMapper {

    public ComicDto entityToDto(ComicsEntity comicsEntity) {
        ComicDto comicDto = ComicDto
                .builder()
                .name(comicsEntity.getName())
                .author(comicsEntity.getAuthor())
                .genres(comicsEntity.getGenres().stream().toList())
                //.imageCoverBase64(Base64.getEncoder().encodeToString(comicsEntity.getCoverImage()))
                .rating(comicsEntity.getRating())
                .votes(comicsEntity.getVotes())
                .description(comicsEntity.getDescription())
                .type(comicsEntity.getType())
                .publishedDate(comicsEntity.getPublishedDate())
                .isUpdated(comicsEntity.getIsUpdated())
                .build();

        if (comicsEntity.getCoverImage() != null) {
            comicDto.setImageCoverBase64(Base64.getEncoder().encodeToString(comicsEntity.getCoverImage()));
        }

        return comicDto;
    }

    public ComicsEntity dtoToEntity(ComicDto comicDto) {
        ComicsEntity comicsEntity = ComicsEntity
                .builder()
                .name(comicDto.getName())
                .author(comicDto.getAuthor())
                .genres(new LinkedHashSet<>(comicDto.getGenres()))
                //.coverImage((Base64.getDecoder().decode(comicDto.getImageCoverBase64())))
                .rating(comicDto.getRating())
                .votes(comicDto.getVotes())
                .description(comicDto.getDescription())
                .type(comicDto.getType())
                .publishedDate(comicDto.getPublishedDate())
                .isUpdated(false)
                .build();

        if (StringUtils.isNotBlank(comicDto.getImageCoverBase64())) {
            comicsEntity.setCoverImage(Base64.getDecoder().decode(comicDto.getImageCoverBase64()));
        }

        return comicsEntity;
    }
}

