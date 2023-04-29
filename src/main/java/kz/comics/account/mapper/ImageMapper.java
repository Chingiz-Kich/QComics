package kz.comics.account.mapper;

import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.repository.entities.ImageEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImageMapper {

    public ImageEntity toEntity(ImageDto imageDto) {
        return ImageEntity.builder()
                .name(imageDto.getName())
                .data(imageDto.getData())
                .build();
    }

    public ImageDto toDto(ImageEntity imageEntity) {
        return ImageDto.builder()
                .name(imageEntity.getName())
                .data(imageEntity.getData())
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
