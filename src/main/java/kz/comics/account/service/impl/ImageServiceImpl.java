package kz.comics.account.service.impl;

import kz.comics.account.mapper.ImageMapper;
import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.repository.ImageRepository;
import kz.comics.account.repository.entities.ImageEntity;
import kz.comics.account.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Override
    public ImageDto save(ImageDto imageDto) {
        log.info("Saving imageDto name: {}", imageDto.getName());
        ImageEntity imageEntity = imageRepository.save(imageMapper.toEntity(imageDto));
        log.info("Saved imageEntity id: {}, name: {}", imageEntity.getName(), imageEntity.getId());
        return imageMapper.toDto(imageEntity);
    }

    @Override
    public List<ImageDto> saveAll(List<ImageDto> imageDtoList) {
        log.info("Saving imageDtoList size: {}", imageDtoList.size());
        List<ImageEntity> imageEntityList = imageRepository.saveAll(imageMapper.toImageEntityList(imageDtoList));
        List<Integer> ids = imageEntityList.stream()
                .map(ImageEntity::getId)
                .toList();
        log.info("Saved imageDtoList size:{}, ids: {}", imageEntityList.size(), ids);
        return imageMapper.toImageDtoList(imageEntityList);
    }

    @Override
    public List<ImageDto> getAllByName(String name) {
        List<ImageEntity> imageEntityList = imageRepository.getImageEntitiesByName(name);
        return imageMapper.toImageDtoList(imageEntityList);
    }
}
