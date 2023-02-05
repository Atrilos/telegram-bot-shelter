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
import pro.sky.telegrambotshelter.model.enums.ShelterType;
import pro.sky.telegrambotshelter.service.UserService;
import pro.sky.telegrambotshelter.utils.KeyboardUtils;

import java.util.EnumSet;

@Component
public class AdoptCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public AdoptCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.ADOPT.getCommand(),
                AvailableCommands.ADOPT.getDescription(),
                AvailableCommands.ADOPT.isTopLevel(),
                EnumSet.allOf(CurrentMenu.class)
        );
        this.userService = userService;
        this.bot = bot;
    }

    public static ReplyKeyboardMarkup createReplyKeyboard(User user) {

        KeyboardButton[] meetButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.MEET_ANIMAL.getDescription());
        KeyboardButton[] papersButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.PAPERS.getDescription());
        KeyboardButton[] transportButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.TRANSPORT_ANIMAL.getDescription());
        KeyboardButton[] catHomeButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.CAT_HOME.getDescription());
        KeyboardButton[] pupHomeButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.PUPPY_HOME.getDescription());
        KeyboardButton[] homeButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.DOG_HOME.getDescription());
        KeyboardButton[] disabledDogHomeButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.DISABLED_DOG_HOME.getDescription());
        KeyboardButton[] communicationButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.COMMUNICATION.getDescription());
        KeyboardButton[] dogHandlersButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.DOG_HANDLERS.getDescription());
        KeyboardButton[] refusalCauseButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.REFUSAL_CAUSE.getDescription());
        KeyboardButton[] backButton =
                KeyboardUtils.createKeyboardButton(CommandDescriptions.TO_MAIN_MENU_DESC);

        if (user.getCurrentShelter() == ShelterType.DOG) {
            return KeyboardUtils.createKeyboard(meetButton, papersButton, transportButton, pupHomeButton, homeButton,
                    disabledDogHomeButton, communicationButton, dogHandlersButton, refusalCauseButton, backButton);
        } else {
            return KeyboardUtils.createKeyboard(meetButton, papersButton, transportButton, catHomeButton, communicationButton, refusalCauseButton, backButton);
        }
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, AvailableCommands.ADOPT.getDescription());
        ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard(user);
        message.replyMarkup(replyKeyboardMarkup);
        bot.execute(message);
        userService.changeCurrentMenu(user, CurrentMenu.ADOPTION);
    }
}
