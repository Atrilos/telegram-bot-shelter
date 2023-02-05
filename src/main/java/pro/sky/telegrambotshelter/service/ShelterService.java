package pro.sky.telegrambotshelter.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.telegrambotshelter.exception.PrimaryKeyNotNullException;
import pro.sky.telegrambotshelter.exception.ShelterNotFoundException;
import pro.sky.telegrambotshelter.model.Shelter;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.ShelterType;
import pro.sky.telegrambotshelter.repository.ShelterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final TelegramCommandBot bot;

    /**
     * Создание двух различных приютов для удобства тестирования
     */
    @PostConstruct
    void initShelterRepo() {
        if (bot.isTestMode()) {
            if (findByIsCatShelter(true).size() == 0) {
                Shelter catShelter = Shelter.builder()
                        .isCatShelter(true)
                        .mapUrl("https://www.google.com/maps/place/55%C2%B043'42.6%22N+37%C2%B026'59.7%22E/@55.728503,37.4477223,17z/data=!3m1!4b1!4m4!3m3!8m2!3d55.7285!4d37.449911")
                        .meetingRules("При первом знакомстве с животным следует...")
                        .openHours("8-16 будние, 8-14 суббота, воскресение - выходной")
                        .papers("Чтобы взять кошку из приюта вам потребуются следующие документы: ...")
                        .catHome("Если вы решили завести кошку, то купите когтеточку")
                        .safetyInstructions("На территории приюта запрещено курить во избежание возникновения пожара")
                        .communication("Советы ветеринара")
                        .about("Приют расположен по улице. Н, д. Р")
                        .transport("Рекомендуется транспортировать кошку в специальной переноске")
                        .refusalCause("Вам могут отказать, если...")
                        .build();
                saveEntity(catShelter);
            }
            if (findByIsCatShelter(false).size() == 0) {
                Shelter dogShelter = Shelter.builder()
                        .isCatShelter(false)
                        .mapUrl("https://www.google.com/maps/place/55%C2%B041'52.3%22N+37%C2%B033'58.9%22E/@55.697873,37.5641843,17z/data=!3m1!4b1!4m4!3m3!8m2!3d55.69787!4d37.566373")
                        .meetingRules("При первом знакомстве с животным следует...")
                        .openHours("8-20 будние, суббота, воскресение - выходные")
                        .papers("Чтобы взять собаку из приюта вам потребуются следующие документы: ...")
                        .dogHome("Если вы решили завести собаку, то купите жевательные игрушки")
                        .dogHandlers("Проверенные кинологи: ...")
                        .disabledDogHome("Если вы решили завести собаку с ограниченными возможностями...")
                        .pupHome("Если вы решили взять щенка...")
                        .safetyInstructions("На территории приюта запрещено курить во избежание возникновения пожара")
                        .communication("Советы кинолога")
                        .about("Приют расположен по улице. Г, д. Ш")
                        .transport("Рекомендуется транспортировать собаку в ошейнике")
                        .refusalCause("Вам могут отказать, если...")
                        .build();
                saveEntity(dogShelter);
            }
        }
    }

    /**
     * Возвращает Shelter
     *
     * @param user пользователь
     */
    public Shelter getShelter(User user) {
        boolean catShelter = user.getCurrentShelter() == ShelterType.CAT;
        return getShelter(catShelter);
    }

    /**
     * Возвращает Shelter
     * @param isCatShelter Является ли приютом для кошек
     */
    public Shelter getShelter(boolean isCatShelter) {
        List<Shelter> shelters = findByIsCatShelter(isCatShelter);
        if (shelters.size() == 0)
            throw new ShelterNotFoundException();

        return shelters.get(0);
    }

    private Shelter updateEntity(Shelter shelter) {
        return shelterRepository.saveAndFlush(shelter);
    }

    private void saveEntity(Shelter shelter) {
        if (shelter.getId() != null) {
            throw new PrimaryKeyNotNullException("%s primary key not null when saving new entity".formatted(shelter));
        }
        shelterRepository.save(shelter);
    }

    private List<Shelter> findByIsCatShelter(boolean isCatShelter) {
        return shelterRepository.findByIsCatShelter(isCatShelter);
    }
}
