package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;

import java.util.EnumSet;

@Component
public class TransportAnimalCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public TransportAnimalCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.TRANSPORT_ANIMAL.getCommand(),
                AvailableCommands.TRANSPORT_ANIMAL.getDescription(),
                AvailableCommands.TRANSPORT_ANIMAL.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {

    }
}
