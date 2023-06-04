package kz.comics.account.service;

import kz.comics.account.model.rate.RateDto;

public interface RateService {
    RateDto saveRate(String comicName, String username, double rating);
    double getRate(String comicName, String username);
    RateDto updateRate(String comicName, String username, double rating);
    String deleteRate(String comicName, String username);
}
