package kz.comics.account.service;

import kz.comics.account.model.comics.ImageDto;

import java.util.List;

public interface ImageService {
    ImageDto save(ImageDto imageDto);
    List<ImageDto> saveAll(List<ImageDto> imageDtoList);
    List<ImageDto> getAllByName(String name);
}
