package kz.comics.account.service;

public interface RateService {
    Integer saveRate(String comicName, String username);
    Integer getRate(String comicName, String username);
    Integer updateRate(String comicName, String username);
    String deleteRate(String comicName, String username);
}
