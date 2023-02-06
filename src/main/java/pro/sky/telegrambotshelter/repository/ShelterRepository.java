package pro.sky.telegrambotshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambotshelter.model.Shelter;

import java.util.List;

@Repository
@Transactional
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    // should be only 2 entries: cat shelter, dog shelter

    List<Shelter> findByIsCatShelter(boolean isCatShelter);

}
