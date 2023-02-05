package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.configuration.UIstrings.CommandDescriptions;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;
import pro.sky.telegrambotshelter.utils.KeyboardUtils;

import java.util.EnumSet;

import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.WHAT_DO_YOU_WANT_TO_KNOW;

@Component
public class ShelterInfoCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public ShelterInfoCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.SHELTER_INFO.getCommand(),
                AvailableCommands.SHELTER_INFO.getDescription(),
                AvailableCommands.SHELTER_INFO.isTopLevel(),
                EnumSet.allOf(CurrentMenu.class)
        );
        this.userService = userService;
        this.bot = bot;
    }

    public static ReplyKeyboardMarkup createReplyKeyboard() {

        KeyboardButton[] aboutButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.ABOUT_SHELTER.getDescription());
        KeyboardButton[] addressButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.SHELTER_ADDRESS.getDescription());
        KeyboardButton[] safetyButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.SAFETY_RULES.getDescription());
        KeyboardButton[] backButton =
                KeyboardUtils.createKeyboardButton(CommandDescriptions.TO_MAIN_MENU_DESC);

        return KeyboardUtils.createKeyboard(aboutButton, addressButton, safetyButton, backButton);
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, WHAT_DO_YOU_WANT_TO_KNOW);
        ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard();
        message.replyMarkup(replyKeyboardMarkup);
        bot.execute(message);
        userService.changeCurrentMenu(user, CurrentMenu.SHELTER_INFO);
    }
}
