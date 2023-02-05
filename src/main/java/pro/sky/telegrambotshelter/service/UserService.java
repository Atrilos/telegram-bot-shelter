package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendContact;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambotshelter.exception.PrimaryKeyNotNullException;
import pro.sky.telegrambotshelter.exception.ShelterNotFoundException;
import pro.sky.telegrambotshelter.exception.UserNotFoundException;
import pro.sky.telegrambotshelter.model.Shelter;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.model.enums.ShelterType;
import pro.sky.telegrambotshelter.repository.ShelterRepository;
import pro.sky.telegrambotshelter.repository.UserRepository;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.*;

/**
 * Сервис для обработки взаимодействий с пользователями
 */
@Service
@EnableAsync
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ShelterRepository shelterRepository;
    private final TelegramCommandBot bot;
    /**
     * Список волонтеров
     */
    private final LinkedList<User> volunteerList = new LinkedList<>();

    @PostConstruct
    public void init() {
        updateVolunteerList();
    }

    /**
     * Метод, возвращающий следующего по порядку волонтера из списка волонтеров
     *
     * @return chatId следующего волонтера
     */
    public Long getNextVolunteer() {
        User first = volunteerList.pollFirst();

        if (first == null) {
            throw new UserNotFoundException("At least one volunteer must be present!");
        }

        volunteerList.offerLast(first);
        return first.getChatId();
    }

    /**
     * Event loop обновляющий список волонтеров
     */
    @Scheduled(cron = "0 */1 * * * *")// every minute
    @Async
    @Transactional
    public void updateVolunteerList() {
        List<User> currentVolunteers = userRepository.findVolunteers();
        volunteerList.retainAll(currentVolunteers);
        for (User user : currentVolunteers)
            if (!volunteerList.contains(user))
                volunteerList.offerLast(user);
    }

    /**
     * Проверка отчетов о питомцах
     */
    @Scheduled(fixedRate = 86400000) // every day
    @Async
    @Transactional
    public void checkAdoptersReports() {
        if (bot.isTestMode())
            return;

        Long availableVolunteerId = getNextVolunteer();
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.YEAR) * 1000 + calendar.get(Calendar.DAY_OF_YEAR);
        for (User user : userRepository.findAll()) {
            if (user.getIsCatAdopterTrial() || user.getIsDogAdopterTrial()) {
                if (today - user.getLastReportDay() > 1 || today - user.getLastPhotoReportDay() > 1) {
                    bot.execute(new SendMessage(user.getChatId(), SHOULD_SEND_REPORT));
                    bot.execute(new SendMessage(availableVolunteerId, MISSING_REPORT));
                    bot.execute(new SendContact(availableVolunteerId, user.getPhoneNumber(), user.getFirstName()));
                }
                if (today - user.getAdoptionDay() >= 30) {
                    bot.execute(new SendMessage(availableVolunteerId, TRIAL_PERIOD_OVER));
                    bot.execute(new SendContact(availableVolunteerId, user.getPhoneNumber(), user.getFirstName()));
                }
            }
        }
    }

    /**
     * Метод, регистрирующий пользователя, если запись о нем отсутствует в БД
     *
     * @param update полученное обновление
     */
    public void registerIfAbsent(Update update) {
        Long chatId = update.message().chat().id();
        if (!isRegistered(chatId)) {
            registerNewUser(
                    update.message().from().firstName(),
                    update.message().from().id()
            );
        }
    }

    /**
     * Метод, добавляющий телефонный номер пользователя при получении контактной информации
     *
     * @param contact {@link com.pengrad.telegrambot.model.Contact контактная информация}
     * @return обновленная сущность из БД
     */
    public User registerContact(Contact contact) {
        User user = userRepository
                .findByChatId(contact.userId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User with id=%d not found".formatted(contact.userId()))
                );
        user.setPhoneNumber(contact.phoneNumber());
        return updateEntity(user);
    }

    /**
     * Регистрация нового пользователя
     *
     * @param firstName имя пользователя
     * @param chatId    уникальный идентификатор чата
     */
    private void registerNewUser(String firstName, Long chatId) {
        User user = User.builder()
                .firstName(firstName)
                .chatId(chatId)
                .isAdmin(false)
                .isVolunteer(false)
                .isDogAdopter(false)
                .isDogAdopterTrial(false)
                .isCatAdopter(false)
                .isCatAdopterTrial(false)
                .currentMenu(CurrentMenu.MAIN)
                .currentShelter(ShelterType.DOG)
                .build();
        saveEntity(user);
    }

    /**
     * Метод, сохраняющий пользователя в БД при условии основного ключа равного null
     *
     * @param user сохраняемый в БД пользователь
     */
    private void saveEntity(User user) {
        if (user.getId() != null) {
            throw new PrimaryKeyNotNullException("%s primary key not null when saving new entity".formatted(user));
        }
        userRepository.save(user);
    }

    /**
     * Метод, обновляющий данные пользователя в БД
     *
     * @param user пользователь, с обновленными данными
     * @return обновленная сущность
     */
    private User updateEntity(User user) {
        return userRepository.saveAndFlush(user);
    }

    /**
     * Метод, проверяющий наличие пользователя в БД по его chatId
     *
     * @param chatId уникальный идентификатор чата
     * @return true - пользователь в базе, false - в ином случае
     */
    private boolean isRegistered(Long chatId) {
        return userRepository
                .findByChatId(chatId)
                .isPresent();
    }

    /**
     * Получение пользователя по его chatId
     *
     * @param chatId уникальный идентификатор чата
     * @return полученная сущность из БД
     * @throws UserNotFoundException пользователь не найден в БД
     */
    public User getUser(Long chatId) {
        return userRepository
                .findByChatId(chatId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User with id=%d not found".formatted(chatId))
                );
    }

    /**
     * Изменение текущего меню для пользователя и сохранение этих данных в БД
     *
     * @param user    текущий пользователь
     * @param newMenu новое значение текущего меню
     */
    public void changeCurrentMenu(User user, CurrentMenu newMenu) {
        user.setCurrentMenu(newMenu);
        updateEntity(user);
    }

    /**
     * Изменение текущего типа приюта
     *
     * @param user        текущий пользователь
     * @param shelterType новое значение типа приюта
     */
    public void changeCurrentShelterType(User user, ShelterType shelterType) {
        user.setCurrentShelter(shelterType);
        updateEntity(user);
    }

    /**
     * Обработка отчета пользователя о питомце"
     *
     * @param update update от пользователя
     * @param user   пользователь, отсылающий отчет
     */
    public void processReport(Update update, User user) {
        String text = update.message().text();
        Long availableVolunteerId = getNextVolunteer();
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.YEAR) * 1000 + calendar.get(Calendar.DAY_OF_YEAR);
        if (text != null) {
            bot.execute(new SendMessage(availableVolunteerId, USER_REPORT));
            bot.execute(new SendMessage(availableVolunteerId, text));
            bot.execute(new SendContact(availableVolunteerId, user.getPhoneNumber(), user.getFirstName()));
            user.setLastReportDay(today);
        } else {
            PhotoSize[] photoSizes = update.message().photo();
            if (photoSizes != null) {
                for (PhotoSize photoSize : photoSizes) {
                    bot.execute(new SendMessage(availableVolunteerId, USER_PHOTO_REPORT));
                    bot.execute(new SendPhoto(availableVolunteerId, photoSize.fileId()));
                    bot.execute(new SendContact(availableVolunteerId, user.getPhoneNumber(), user.getFirstName()));
                    user.setLastPhotoReportDay(today);
                }
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
        List<Shelter> shelters = shelterRepository.findByIsCatShelter(catShelter);
        if (shelters.size() == 0)
            throw new ShelterNotFoundException();

        return shelters.get(0);
    }


}
