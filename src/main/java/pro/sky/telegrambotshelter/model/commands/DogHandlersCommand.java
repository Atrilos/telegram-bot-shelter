package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.*;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.*;
import pro.sky.telegrambotshelter.service.UserService;

import java.util.EnumSet;


@Component
public class DogHandlersCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

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
        addAlias(AvailableCommands.DOG_HANDLERS.getDescription());
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        Shelter shelter = userService.getShelter(user);
        SendMessage message = new SendMessage(chatId, shelter.getDogHandlers());
        message.replyMarkup(AdoptCommand.createReplyKeyboard(user));
        bot.execute(message);
    }
}
