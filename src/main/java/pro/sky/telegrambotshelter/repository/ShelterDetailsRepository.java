package pro.sky.telegrambotshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambotshelter.model.ShelterDetails;

@Repository
public interface ShelterDetailsRepository extends JpaRepository<ShelterDetails, Long> {
}
