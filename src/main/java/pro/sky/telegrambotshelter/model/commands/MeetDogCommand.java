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
public class MeetDogCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;
    private final String meetDog = "meetDog";

    public MeetDogCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.MEET_DOG.getCommand(),
                AvailableCommands.MEET_DOG.getDescription(),
                AvailableCommands.MEET_DOG.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        addAllAliases(List.of(AvailableCommands.MEET_DOG.getDescription()));
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, meetDog);
        message.replyMarkup(AdoptCommand.createReplyKeyboard());
        bot.execute(message);
    }
}
