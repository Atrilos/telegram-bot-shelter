package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambotshelter.exception.PrimaryKeyNotNullException;
import pro.sky.telegrambotshelter.exception.UserNotFoundException;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * Сервис для обработки взаимодействий с пользователями
 */
@Service
@EnableAsync
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
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
    @Scheduled(cron = "0 */1 * * * *")
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
     * Метод, регистрирующий пользователя, если запись о нем отсутствует в БД
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
     * @param firstName имя пользователя
     * @param chatId уникальный идентификатор чата
     */
    private void registerNewUser(String firstName, Long chatId) {
        User user = User.builder()
                .firstName(firstName)
                .chatId(chatId)
                .isAdmin(false)
                .isVolunteer(false)
                .currentMenu(CurrentMenu.MAIN)
                .build();
        saveEntity(user);
    }

    /**
     * Метод, сохраняющий пользователя в БД при условии основного ключа равного null
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
     * @param user пользователь, с обновленными данными
     * @return обновленная сущность
     */
    private User updateEntity(User user) {
        return userRepository.saveAndFlush(user);
    }

    /**
     * Метод, проверяющий наличие пользователя в БД по его chatId
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
     * @param chatId уникальный идентификатор чата
     * @throws UserNotFoundException пользователь не найден в БД
     * @return полученная сущность из БД
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
     * @param user текущий пользователь
     * @param newMenu новое значение текущего меню
     */
    public void changeCurrentMenu(User user, CurrentMenu newMenu) {
        user.setCurrentMenu(newMenu);
        updateEntity(user);
    }
}
