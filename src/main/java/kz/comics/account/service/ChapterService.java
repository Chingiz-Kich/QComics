package kz.comics.account.service;

import kz.comics.account.model.chapter.ChapterDto;
import kz.comics.account.model.chapter.ChapterSaveDto;
import kz.comics.account.model.chapter.ChapterUpdate;

import java.util.List;

public interface ChapterService {
    ChapterDto save(ChapterSaveDto chapterSaveDto);
    ChapterDto getById(Integer id);
    List<ChapterDto> getAll();
    List<ChapterDto> getByComicName(String comicName);
    String deleteById(Integer id);
    ChapterDto update(ChapterUpdate chapterUpdate);
    String deleteAll();
}
