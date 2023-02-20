package pro.sky.telegrambotshelter.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import pro.sky.telegrambotshelter.model.Pet;
import pro.sky.telegrambotshelter.model.Shelter;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.ShelterType;
import pro.sky.telegrambotshelter.repository.PetRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@DependsOn("shelterService")
public class PetService {

    private final PetRepository petRepository;
    private final ShelterService shelterService;
    private final TelegramCommandBot bot;

    @PostConstruct
    void initPetRepo() {
        if (bot.isTestMode()) {
            if (getPetsByType(ShelterType.CAT).size() == 0) {
                Shelter catShelter = shelterService.getShelter(true);
                saveAll(List.of(
                        Pet.builder().name("Барсик").petType(ShelterType.CAT).color("Серый").shelter(catShelter).build(),
                        Pet.builder().name("Лиса").petType(ShelterType.CAT).color("Бурый").shelter(catShelter).build(),
                        Pet.builder().name("Мушка").petType(ShelterType.CAT).color("Белый").shelter(catShelter).build())
                );
            }
            if (getPetsByType(ShelterType.DOG).size() == 0) {
                Shelter dogShelter = shelterService.getShelter(false);
                saveAll(List.of(
                        Pet.builder().name("Бобик").petType(ShelterType.DOG).color("Коричневый").shelter(dogShelter).build(),
                        Pet.builder().name("Черныш").petType(ShelterType.DOG).color("Черный").shelter(dogShelter).build(),
                        Pet.builder().name("Голд").petType(ShelterType.DOG).color("Желтый").shelter(dogShelter).build()
                ));
            }
        }
    }

    private void saveAll(Iterable<Pet> pets) {
        petRepository.saveAll(pets);
    }

    private List<Pet> getPetsByType(ShelterType petType) {
        return petRepository.findByPetType(petType);
    }
}
