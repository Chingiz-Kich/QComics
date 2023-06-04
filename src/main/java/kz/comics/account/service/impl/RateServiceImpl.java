package kz.comics.account.service.impl;

import kz.comics.account.model.rate.RateDto;
import kz.comics.account.repository.ComicsRepository;
import kz.comics.account.repository.RateRepository;
import kz.comics.account.repository.UserRepository;
import kz.comics.account.repository.entities.ComicsEntity;
import kz.comics.account.repository.entities.RateEntity;
import kz.comics.account.repository.entities.UserEntity;
import kz.comics.account.service.ComicService;
import kz.comics.account.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;
    private final ComicsRepository comicsRepository;
    private final UserRepository userRepository;

    private final ComicService comicService;

    @Override
    @Transactional
    public RateDto saveRate(String comicName, String username, double rating) {
        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", comicName)));

        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        if (rateRepository.findRateEntityByComicsAndAndUser(comicsEntity, userEntity).isPresent()) {
            throw new IllegalStateException("This rate already exist");
        }

        RateEntity rateEntity = RateEntity
                .builder()
                .rating(rating)
                .comics(comicsEntity)
                .user(userEntity)
                .build();

        rateEntity = rateRepository.save(rateEntity);
        comicService.upVotes(comicName);
        comicService.updateRating(comicName, rateEntity.getRating());
        return RateDto
                .builder()
                .username(rateEntity.getUser().getUsername())
                .comicName(rateEntity.getComics().getName())
                .rating(rateEntity.getRating())
                .build();

    }

    @Override
    @Transactional
    public double getRate(String comicName, String username) {
        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", comicName)));

        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        Optional<RateEntity> optionalRate = rateRepository.findRateEntityByComicsAndAndUser(comicsEntity, userEntity);

        if (optionalRate.isEmpty()) {
            return -1d;
        }

        return optionalRate.get().getRating();

    }

    @Override
    @Transactional
    public RateDto updateRate(String comicName, String username, double rating) {
        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", comicName)));

        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        Optional<RateEntity> optionalRate = rateRepository.findRateEntityByComicsAndAndUser(comicsEntity, userEntity);

        if (optionalRate.isEmpty()) {
            throw new IllegalStateException(String.format("Cannot find rate with comicName: %s and username: %s", comicName, username));
        }

        RateEntity rateEntity = optionalRate.get();

        rateEntity.setRating(rating);
        rateRepository.save(rateEntity);

        /** THERE ARE SHOULD BE LOGIC TO UPDATE RATING COMICS **/


        return RateDto
                .builder()
                .username(rateEntity.getUser().getUsername())
                .comicName(rateEntity.getComics().getName())
                .rating(rateEntity.getRating())
                .build();
    }

    @Override
    @Transactional
    public String deleteRate(String comicName, String username) {
        ComicsEntity comicsEntity = comicsRepository.getComicsEntitiesByName(comicName)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find comic with name: %s", comicName)));

        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find user with username: %s", username)));

        Optional<RateEntity> optionalRate = rateRepository.findRateEntityByComicsAndAndUser(comicsEntity, userEntity);

        if (optionalRate.isEmpty()) {
            throw new IllegalStateException(String.format("Cannot find rate with comicName: %s and username: %s", comicName, username));
        }

        RateEntity rateEntity = optionalRate.get();
        rateRepository.delete(rateEntity);
        comicService.downVotes(comicName);
        comicService.updateRating(comicName, rateEntity.getRating() * (-1));
        return "Rate deleted";
    }
}
