package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;

import static pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages.UNSUPPORTED_RESPONSE_MSG;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {
    private final TelegramCommandBot bot;
    private final UserService userService;

    public User authenticateUser(Update update) {
        userService.registerIfAbsent(update);
        return userService.getUser(update.message().chat().id());
    }

    public void processMessageByType(Update update, User user) {
        if (update.message().contact() != null) {
            processContact(update);
        } else {
            processTextMessage(update, user);
        }
    }

    private void processTextMessage(Update update, User user) {
        log.info("MainService processTextMessage");
        Long chatId = update.message().chat().id();
        ExecutableBotCommand receivedCommand = bot.getCommand(update.message().text());
        if (receivedCommand == null) {
            bot.execute(new SendMessage(chatId, UNSUPPORTED_RESPONSE_MSG));
        } else {
            receivedCommand.execute(bot, update, user, userService);
        }
    }

    private void processContact(Update update) {
        User updatedUser = userService.registerContact(update.message().contact());
        bot.getCommand(AvailableCommands.CALL_STAFF.getValue())
                .execute(bot, update, updatedUser, userService);
    }
}
