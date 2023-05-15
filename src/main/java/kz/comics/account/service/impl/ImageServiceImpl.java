package kz.comics.account.service.impl;

import kz.comics.account.mapper.ImageMapper;
import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.model.comics.ImageSaveDto;
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
    public ImageDto save(ImageSaveDto imageSaveDto) {
        log.info("Saving imageDto name: {}", imageSaveDto.getName());
        ImageEntity imageEntity = imageRepository.save(imageMapper.toEntity(imageSaveDto));
        log.info("Saved imageEntity id: {}, name: {}", imageEntity.getName(), imageEntity.getId());
        return imageMapper.toDto(imageEntity);
    }

    @Override
    public List<Integer> saveAll(List<ImageSaveDto> imageSaveDtos) {
        log.info("Saving imageDtoList size: {}", imageSaveDtos.size());
        List<ImageEntity> imageEntityList = imageRepository.saveAll(imageMapper.toImageEntityList(imageSaveDtos));
        List<Integer> ids = imageEntityList.stream()
                .map(ImageEntity::getId)
                .toList();
        log.info("Saved imageDtoList size:{}, ids: {}", imageEntityList.size(), ids);
        return imageMapper.toImageDtoList(imageEntityList)
                .stream()
                .map(ImageDto::getId)
                .toList();
    }

    @Override
    public List<ImageDto> getAllByName(String name) {
        List<ImageEntity> imageEntityList = imageRepository.getImageEntitiesByName(name);
        return imageMapper.toImageDtoList(imageEntityList);
    }

    @Override
    public ImageEntity getImageEntityById(Integer id) {
        return imageRepository.getImageEntityById(id);
    }

    @Override
    public List<Integer> getListIdByName(String name) {
        List<ImageEntity> imageEntityList = imageRepository.getImageEntitiesByName(name);
        return imageEntityList.stream()
                .map(ImageEntity::getId)
                .toList();
    }

    @Override
    public ImageDto getImageById(Integer id) {
        return imageMapper.toDto(imageRepository.getImageEntityById(id));
    }
}
