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
public class RefusalCauseCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public RefusalCauseCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.REFUSAL_CAUSE.getCommand(),
                AvailableCommands.REFUSAL_CAUSE.getDescription(),
                AvailableCommands.REFUSAL_CAUSE.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        addAlias(AvailableCommands.REFUSAL_CAUSE.getDescription());
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        Shelter shelter = userService.getShelter(user);
        SendMessage message = new SendMessage(chatId, shelter.getRefusalCause());
        message.replyMarkup(AdoptCommand.createReplyKeyboard(user));
        bot.execute(message);
    }
}
