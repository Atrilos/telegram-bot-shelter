package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;
import pro.sky.telegrambotshelter.model.enums.*;
import java.util.List;

import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.UNSUPPORTED_COMMAND;

/**
 * Основной сервис для обработки поступающих сообщений
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {
    private final TelegramCommandBot bot;
    private final UserService userService;
    private final List<ExecutableBotCommand> botCommands;

    /**
     * Метод, производящий аутентификацию пользователя, используя данных из обновления.
     * Получает данные о пользователе из БД, при необходимости создает нового пользователя
     *
     * @param update полученное обновление
     * @return данные о пользователе из БД
     */
    public User authenticateUser(Update update) {
        userService.registerIfAbsent(update);
        return userService.getUser(update.message().chat().id());
    }

    /**
     * Разделение полученных сообщений по типу
     *
     * @param update полученное обновление
     * @param user   данные о пользователе из БД
     */
    public void processMessageByType(Update update, User user) {
        if (update.message().contact() != null) {
            processContact(update);
        } else {
            processTextMessage(update, user);
        }
    }

    /**
     * Обработка текстовых сообщений
     *
     * @param update полученное обновление
     * @param user   данные о пользователе из БД
     */
    private void processTextMessage(Update update, User user) {
        log.info("MainService processTextMessage");
        Long chatId = update.message().chat().id();
        ExecutableBotCommand receivedCommand =
                botCommands.stream()
                        .filter(c -> c.isSupported(update.message().text(), user.getCurrentMenu()))
                        .findFirst()
                        .orElse(null);
        if (receivedCommand == null) {
            bot.execute(new SendMessage(chatId, UNSUPPORTED_COMMAND));
        } else {
            receivedCommand.execute(update, user);
        }
        if (receivedCommand == null && user.getCurrentMenu() == CurrentMenu.REPORT){
            userService.processReport(update, user);
        }
        if (bot.isTestMode()){
            System.out.println("current menu " + user.getCurrentMenu());
            System.out.println("current shelter " + user.getCurrentShelter());
        }
    }

    /**
     * Обработка полученного {@link com.pengrad.telegrambot.model.Contact контакта}
     *
     * @param update полученное обновление
     */
    private void processContact(Update update) {
        User updatedUser = userService.registerContact(update.message().contact());
        if (updatedUser.getCurrentMenu() == CurrentMenu.CALL_STAFF) {
            botCommands
                    .stream()
                    .filter(c -> c.isSupported(AvailableCommands.CALL_STAFF.getCommand(), CurrentMenu.CALL_STAFF))
                    .findAny().orElseThrow()
                    .execute(update, updatedUser);
        }
    }
}
