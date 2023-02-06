package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;
import pro.sky.telegrambotshelter.utils.KeyboardUtils;

import java.util.EnumSet;
import java.util.List;

import static pro.sky.telegrambotshelter.configuration.UIstrings.CommandDescriptions.ALT_TO_MAIN_MENU;
import static pro.sky.telegrambotshelter.configuration.UIstrings.CommandDescriptions.TO_MAIN_MENU_DESC;
import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.*;
import static pro.sky.telegrambotshelter.utils.KeyboardUtils.createKeyboard;
import static pro.sky.telegrambotshelter.utils.KeyboardUtils.createKeyboardButton;

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

    public static ReplyKeyboardMarkup createReplyKeyboardShelterKnown() {

        return createKeyboard(
                createKeyboardButton(AvailableCommands.SHELTER_INFO.getDescription()),
                createKeyboardButton(AvailableCommands.ADOPT.getDescription()),
                createKeyboardButton(AvailableCommands.REPORT.getDescription()),
                createKeyboardButton(AvailableCommands.HELP.getCommand()),
                createKeyboardButton(AvailableCommands.CALL_STAFF.getDescription())
        );

    }

    @PostConstruct
    public void init() {
        addAllAliases(List.of(ALT_TO_MAIN_MENU, TO_MAIN_MENU_DESC));
    }

    private ReplyKeyboardMarkup createReplyKeyboardSelectShelter() {

        KeyboardButton[] dogShelterButton =
                KeyboardUtils.createKeyboardButton(DOG_SHELTER);
        KeyboardButton[] catShelterButton =
                KeyboardUtils.createKeyboardButton(CAT_SHELTER);

        return KeyboardUtils.createKeyboard(dogShelterButton, catShelterButton);
    }

    @Override
    public void execute(Update update, User user) {
        userService.changeCurrentMenu(user, CurrentMenu.MAIN);
        Long chatId = update.message().chat().id();
        String responseMsg = START_RESPONSE.formatted(update.message().from().firstName());
        SendMessage welcomeMessage = new SendMessage(chatId, responseMsg);
        if (user.getCurrentShelter() == null) {
            bot.execute(welcomeMessage);
            SendMessage request = new SendMessage(chatId, SELECT_SHELTER);
            ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboardSelectShelter();
            request.replyMarkup(replyKeyboardMarkup);
            bot.execute(request);
            userService.changeCurrentMenu(user, CurrentMenu.SELECT_SHELTER);
        } else {
            ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboardShelterKnown();
            welcomeMessage.replyMarkup(replyKeyboardMarkup);
            bot.execute(welcomeMessage);
        }
    }
}
