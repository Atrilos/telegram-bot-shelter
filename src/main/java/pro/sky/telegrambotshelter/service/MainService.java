package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.UserState;

import static pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages.UNSUPPORTED_RESPONSE_MSG;

@Service
@RequiredArgsConstructor
public class MainService {
    private final TelegramCommandBot bot;
    private final UserService userService;

    private void processTextMessage(Update update, User user) {
        Long chatId = update.message().chat().id();
        ExecutableBotCommand receivedCommand = bot.getCommand(update.message().text());
        if (user.getState() == UserState.BASIC_STATE && receivedCommand == null) {
            bot.execute(new SendMessage(chatId, UNSUPPORTED_RESPONSE_MSG));
        } else if (user.getState() == UserState.CALL_STAFF_STATE) {
            userService.changeUserState(user, UserState.BASIC_STATE);
        } else {
            receivedCommand.execute(bot, update, userService);
        }
    }

    public void processMessageByType(Update update, User user) {
        if (update.message().contact() != null) {
            processContact(update, user);
        } else {
            processTextMessage(update, user);
        }
    }

    private void processContact(Update update, User user) {
        if (user.getState() == UserState.CALL_STAFF_STATE) {
            userService.registerContact(update.message().contact());
            bot.getCommand(AvailableCommands.CALL_STAFF.getValue())
                    .execute(bot, update, userService);
        }
    }

    public User authenticateUser(Update update) {
        userService.registerIfAbsent(update);
        return userService.getUser(update.message().chat().id());
    }
}
