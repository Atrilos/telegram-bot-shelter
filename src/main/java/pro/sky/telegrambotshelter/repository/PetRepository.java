package pro.sky.telegrambotshelter.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambotshelter.model.Pet;
import pro.sky.telegrambotshelter.model.enums.ShelterType;

import java.util.List;

@Repository
@Transactional
public interface PetRepository extends JpaRepository<Pet, Long> {

    @EntityGraph(attributePaths = {"shelter"})
    List<Pet> findByPetType(ShelterType petType);
}
