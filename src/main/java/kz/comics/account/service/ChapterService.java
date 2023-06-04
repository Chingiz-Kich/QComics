package kz.comics.account.service;

import kz.comics.account.model.chapter.*;

import java.util.List;

public interface ChapterService {
    ChapterDto saveByComicName(ChapterSaveByCName chapterSaveDto);
    ChapterDto saveByComicId(ChapterSaveByCId chapterSaveDto);
    ChapterDto getById(Integer id);
    List<ChapterDto> getAll();
    List<ChapterDto> getByComicName(String comicName);
    List<ChapterDto> getByComicId(Integer comicId);
    String deleteById(Integer id);
    ChapterDto update(ChapterUpdate chapterUpdate);
    String deleteAll();
}
