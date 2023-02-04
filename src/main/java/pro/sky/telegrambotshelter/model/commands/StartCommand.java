package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.*;
import pro.sky.telegrambotshelter.service.UserService;
import pro.sky.telegrambotshelter.utils.KeyboardUtils;
import java.util.EnumSet;

import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.*;

@Component
public class StartCommand extends ExecutableBotCommand {
    private final TelegramCommandBot bot;
    private final UserService userService;

    public StartCommand(TelegramCommandBot bot, UserService userService) {
        super(AvailableCommands.START.getCommand(),
                AvailableCommands.START.getDescription(),
                AvailableCommands.START.isTopLevel(),
                EnumSet.allOf(CurrentMenu.class)
        );
        this.bot = bot;
        this.userService = userService;
    }

    private ReplyKeyboardMarkup createReplyKeyboard() {

        KeyboardButton[] dogShelterButton =
                KeyboardUtils.createKeyboardButton(DOG_SHELTER);
        KeyboardButton[] catShelterButton =
                KeyboardUtils.createKeyboardButton(CAT_SHELTER);

        return KeyboardUtils.createKeyboard(dogShelterButton, catShelterButton);
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        String responseMsg = START_RESPONSE.formatted(update.message().from().firstName());
        bot.execute(new SendMessage(chatId, responseMsg));
        SendMessage message = new SendMessage(chatId, SELECT_SHELTER);
        ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard();
        message.replyMarkup(replyKeyboardMarkup);
        bot.execute(message);
        userService.changeCurrentMenu(user, CurrentMenu.SELECT_SHELTER);
    }
}
