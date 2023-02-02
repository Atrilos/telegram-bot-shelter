package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;

import java.util.EnumSet;
import java.util.List;

import static pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages.START_RESPONSE_MSG;
import static pro.sky.telegrambotshelter.model.enums.AvailableCommands.*;
import static pro.sky.telegrambotshelter.utils.KeyboardUtils.createKeyboard;
import static pro.sky.telegrambotshelter.utils.KeyboardUtils.createKeyboardButton;

@Component
public class StartCommand extends ExecutableBotCommand {
    private final TelegramCommandBot bot;
    private final UserService userService;


    public StartCommand(TelegramCommandBot bot, UserService userService) {
        super(START.getCommand(),
                START.getDescription(),
                START.isTopLevel(),
                EnumSet.allOf(CurrentMenu.class)
        );
        this.bot = bot;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        addAllAliases(List.of("Назад", "В главное меню"));
    }

    @Override
    public void execute(Update update, User user) {
        userService.changeCurrentMenu(user, CurrentMenu.MAIN);
        Long chatId = update.message().chat().id();
        String responseMsg = START_RESPONSE_MSG.formatted(update.message().from().firstName());
        SendMessage request = new SendMessage(chatId, responseMsg);
        request.replyMarkup(
                createKeyboard(
                        createKeyboardButton(SHELTER_INFO.getDescription(), false),
                        createKeyboardButton(ADOPT.getDescription(), false),
                        createKeyboardButton(REPORT.getDescription(), false),
                        createKeyboardButton(HELP.getCommand(), false),
                        createKeyboardButton(CALL_STAFF.getDescription(), false)
                )
        );
        bot.execute(request);
    }
}
