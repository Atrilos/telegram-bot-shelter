package pro.sky.telegrambotshelter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambotshelter.model.Shelter;

import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    // should be only 2 entries: cat shelter, dog shelter
    @Query("select s from Shelter s where s.isCatShelter = true")
    List<Shelter> findByIsCatShelter(Boolean isCatShelter);

}
