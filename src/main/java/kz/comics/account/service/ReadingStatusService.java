package kz.comics.account.service;

import kz.comics.account.model.chapter.ChapterReadStatus;

public interface ReadingStatusService {
    String markChapterAsRead(String username, Integer chapterId);
    String markChapterAsUnread(String username, Integer chapterId);
    ChapterReadStatus isChapterReadByUser(String username, Integer chapterId);
}
