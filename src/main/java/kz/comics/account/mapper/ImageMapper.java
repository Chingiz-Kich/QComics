package kz.comics.account.mapper;

import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.repository.entities.ImageEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class ImageMapper {

    public ImageEntity toEntity(ImageDto imageDto) {
        return ImageEntity.builder()
                .id(imageDto.getId())
                .name(imageDto.getName())
                .data(Base64.getDecoder().decode(imageDto.getBase64()))
                .build();
    }

    public ImageDto toDto(ImageEntity imageEntity) {
        return ImageDto.builder()
                .id(imageEntity.getId())
                .name(imageEntity.getName())
                .base64(Base64.getEncoder().encodeToString(imageEntity.getData()))
                .build();
    }

    public List<ImageDto> toImageDtoList(List<ImageEntity> imageEntityList) {
        List<ImageDto> imageDtoList = new ArrayList<>();
        imageEntityList.forEach(imageEntity -> imageDtoList.add(this.toDto(imageEntity)));
        return imageDtoList;
    }

    public List<ImageEntity> toImageEntityList(List<ImageDto> imageDtoList) {
        List<ImageEntity> imageEntityList = new ArrayList<>();
        imageDtoList.forEach(imageDto -> imageEntityList.add(this.toEntity(imageDto)));
        return imageEntityList;
    }
}
