package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;

import java.util.EnumSet;
import java.util.List;

@Component
public class DisabledDogHomeCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;
    private final String disabledDogHomeCommand = "disabledDogHomeCommand";

    public DisabledDogHomeCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.DISABLED_DOG_HOME.getCommand(),
                AvailableCommands.DISABLED_DOG_HOME.getDescription(),
                AvailableCommands.DISABLED_DOG_HOME.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        addAllAliases(List.of(AvailableCommands.DISABLED_DOG_HOME.getDescription()));
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, disabledDogHomeCommand);
        message.replyMarkup(AdoptCommand.createReplyKeyboard());
        bot.execute(message);
    }
}
