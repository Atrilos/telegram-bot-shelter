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
public class DogHandlersCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;
    private final String dogHandlers = "dogHandlers";

    public DogHandlersCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.DOG_HANDLERS.getCommand(),
                AvailableCommands.DOG_HANDLERS.getDescription(),
                AvailableCommands.DOG_HANDLERS.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        addAllAliases(List.of(AvailableCommands.DOG_HANDLERS.getDescription()));
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, dogHandlers);
        message.replyMarkup(AdoptCommand.createReplyKeyboard());
        bot.execute(message);
    }
}
