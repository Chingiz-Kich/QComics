package kz.comics.account.service;

import kz.comics.account.model.comics.ChapterDto;
import kz.comics.account.model.comics.ChapterSaveDto;

public interface ChapterService {
    ChapterDto save(ChapterSaveDto chapterSaveDto);
    ChapterDto getById(Integer id);
}
