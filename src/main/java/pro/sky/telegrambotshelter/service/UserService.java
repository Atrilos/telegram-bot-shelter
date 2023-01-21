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

@Service
@EnableAsync
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final LinkedList<User> volunteerList = new LinkedList<>();

    @PostConstruct
    public void init() {
        updateVolunteerList();
    }

    public Long getNextVolunteer() {
        User first = volunteerList.pollFirst();

        if (first == null) {
            throw new UserNotFoundException("At least one volunteer must be present!");
        }

        volunteerList.offerLast(first);
        return first.getChatId();
    }

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


    public void registerIfAbsent(Update update) {
        Long chatId = update.message().chat().id();
        if (!isRegistered(chatId)) {
            registerNewUser(
                    update.message().from().firstName(),
                    update.message().from().id()
            );
        }
    }

    public User registerContact(Contact contact) {
        User user = userRepository
                .findByChatId(contact.userId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User with id=%d not found".formatted(contact.userId()))
                );
        user.setPhoneNumber(contact.phoneNumber());
        return updateEntity(user);
    }

    private void registerNewUser(String firstName, Long chatId) {
        User user = User.builder()
                .firstName(firstName)
                .chatId(chatId)
                .currentMenu(CurrentMenu.MAIN)
                .build();
        saveEntity(user);
    }

    private void saveEntity(User user) {
        if (user.getId() != null) {
            throw new PrimaryKeyNotNullException("%s primary key not null when saving new entity".formatted(user));
        }
        userRepository.save(user);
    }

    private User updateEntity(User user) {
        return userRepository.saveAndFlush(user);
    }

    private boolean isRegistered(Long chatId) {
        return userRepository
                .findByChatId(chatId)
                .isPresent();
    }

    public User getUser(Long chatId) {
        return userRepository
                .findByChatId(chatId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User with id=%d not found".formatted(chatId))
                );
    }

    public void changeCurrentMenu(User user, CurrentMenu newMenu) {
        user.setCurrentMenu(newMenu);
        updateEntity(user);
    }
}
