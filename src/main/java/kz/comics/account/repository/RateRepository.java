package kz.comics.account.repository;

import kz.comics.account.repository.entities.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<RateEntity, Integer> {
}
