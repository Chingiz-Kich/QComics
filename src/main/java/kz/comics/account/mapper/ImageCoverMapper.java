package kz.comics.account.mapper;

import kz.comics.account.model.comics.ImageCoverDto;
import kz.comics.account.repository.entities.ImageCoverEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@AllArgsConstructor
public class ImageCoverMapper {

    public ImageCoverEntity toEntity(ImageCoverDto imageCoverDto) {
        return ImageCoverEntity.builder()
                .name(imageCoverDto.getName())
                .data(Base64.getDecoder().decode(imageCoverDto.getBase64()))
                .build();
    }

    public ImageCoverDto toDto(ImageCoverEntity imageCoverEntity) {
        return ImageCoverDto.builder()
                .name(imageCoverEntity.getName())
                .base64(Base64.getEncoder().encodeToString(imageCoverEntity.getData()))
                .build();
    }
}
