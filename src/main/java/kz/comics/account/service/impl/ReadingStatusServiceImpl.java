package kz.comics.account.service.impl;

import kz.comics.account.model.chapter.ChapterReadStatus;
import kz.comics.account.repository.ChapterRepository;
import kz.comics.account.repository.ReadingStatusRepository;
import kz.comics.account.repository.UserRepository;
import kz.comics.account.repository.entities.ChapterEntity;
import kz.comics.account.repository.entities.ReadingStatusEntity;
import kz.comics.account.repository.entities.UserEntity;
import kz.comics.account.service.ReadingStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadingStatusServiceImpl implements ReadingStatusService {

    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;
    private final ReadingStatusRepository readingStatusRepository;

    @Override
    public String markChapterAsRead(String username, Integer chapterId) {
        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        ChapterEntity chapterEntity = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with id: %s", chapterId)));

        ReadingStatusEntity readingStatusEntity = ReadingStatusEntity
                .builder()
                .userEntity(userEntity)
                .chapterEntity(chapterEntity)
                .readDate(LocalDate.now())
                .build();

        readingStatusRepository.save(readingStatusEntity);
        return "Marked";
    }

    @Override
    public String markChapterAsUnread(String username, Integer chapterId) {
        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        ChapterEntity chapterEntity = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with id: %s", chapterId)));

        readingStatusRepository.deleteByUserEntityAndChapterEntity(userEntity, chapterEntity);
        return "Marked";
    }

    @Override
    public ChapterReadStatus isChapterReadByUser(String username, Integer chapterId) {

        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        ChapterEntity chapterEntity = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find chapter with id: %s", chapterId)));

        Optional<ReadingStatusEntity> readingStatusEntity = readingStatusRepository.findByUserEntityAndChapterEntity(userEntity, chapterEntity);

        if (readingStatusEntity.isPresent()) {
            return ChapterReadStatus
                    .builder()
                    .isRead(true)
                    .readDate(readingStatusEntity.get().getReadDate())
                    .build();
        } else {
            return ChapterReadStatus
                    .builder()
                    .isRead(false)
                    .readDate(null)
                    .build();
        }
    }
}
