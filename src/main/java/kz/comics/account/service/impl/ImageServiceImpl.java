package kz.comics.account.service.impl;

import kz.comics.account.mapper.ImageMapper;
import kz.comics.account.model.comics.ImageDto;
import kz.comics.account.model.comics.ImageSaveDto;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.ImageRepository;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.repository.entities.ImageEntity;
import kz.comics.account.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ChapterRepository chapterRepository;
    private final ComicsRepository comicsRepository;
    private final ImageMapper imageMapper;

    @Override
    @Transactional
    public ImageDto save(ImageSaveDto imageSaveDto) {
        log.info("Saving imageDto chapter name: {}", imageSaveDto.getChapterName());
        ImageEntity imageEntity = imageRepository.save(imageMapper.toEntity(imageSaveDto));
        log.info("Saved imageEntity id: {}, chapter name: {}", imageEntity.getChapterEntity().getName(), imageEntity.getId());
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
    @Transactional
    public ImageEntity downloadById(Integer id) {
        return imageRepository.getImageEntityById(id);
    }

    @Override
    @Transactional
    public ImageDto getById(Integer id) {
        return imageMapper.toDto(imageRepository.getImageEntityById(id));
    }

    @Override
    public String deleteAll() {
        imageRepository.deleteAll();
        return "All images deleted";
    }

    @Override
    @Transactional
    public List<ImageDto> getAllByChapterNameAndComicName(String chapterName, String comicName) {
        ChapterEntity chapterEntity = chapterRepository.getByName(chapterName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with name: %s", chapterName)));

        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", comicName)));

        List<ImageEntity> imageEntityList = imageRepository.getAllByChapterEntityAndComicsEntity(chapterEntity, comicsEntity);

        return imageMapper.toImageDtoList(imageEntityList);
    }

    @Override
    @Transactional
    public List<Integer> getAllIdsByChapterAndComicName(String chapterName, String comicName) {
        return this.getAllByChapterNameAndComicName(chapterName, comicName)
                .stream()
                .map(ImageDto::getId)
                .toList();
    }
}
