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
public class StoreContactInfoCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public StoreContactInfoCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.STORE_CONTACT_INFO.getCommand(),
                AvailableCommands.STORE_CONTACT_INFO.getDescription(),
                AvailableCommands.STORE_CONTACT_INFO.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {

    }
}
