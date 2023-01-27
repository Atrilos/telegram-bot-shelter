package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
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

    static ReplyKeyboardMarkup createReplyKeyboard() {
        KeyboardButton[] meetButton =
//                KeyboardUtils.createKeyboardButton("Правила знакомства с собакой");
                KeyboardUtils.createKeyboardButton(AvailableCommands.MEET_DOG.getDescription());
        KeyboardButton[] papersButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.PAPERS.getDescription());
        KeyboardButton[] transportButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.TRANSPORT_ANIMAL.getDescription());
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
                KeyboardUtils.createKeyboardButton(AvailableCommands.TO_MAIN_MENU.getDescription());

        return KeyboardUtils.createKeyboard(meetButton, papersButton, transportButton, pupHomeButton, homeButton,
                disabledDogHomeButton, communicationButton, dogHandlersButton, refusalCauseButton, backButton);
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, "Забрать собаку из приюта");
        ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard();
        message.replyMarkup(replyKeyboardMarkup);
        bot.execute(message);
        userService.changeCurrentMenu(user, CurrentMenu.ADOPTION);
    }
}
