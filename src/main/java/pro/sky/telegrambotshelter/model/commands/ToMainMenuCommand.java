package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;

import java.util.EnumSet;

@Component
public class ToMainMenuCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public ToMainMenuCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.TO_MAIN_MENU.getCommand(),
                AvailableCommands.TO_MAIN_MENU.getDescription(),
                AvailableCommands.TO_MAIN_MENU.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION, CurrentMenu.REPORT, CurrentMenu.SHELTER_INFO, CurrentMenu.CALL_STAFF)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        addAlias(AvailableCommands.TO_MAIN_MENU.getDescription());
    }

    @Override
    public void execute(Update update, User user) {
        userService.changeCurrentMenu(user, CurrentMenu.MAIN);
    }
}
