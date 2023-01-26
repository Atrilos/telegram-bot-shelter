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
public class ShelterInfoCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public ShelterInfoCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.SHELTER_INFO.getCommand(),
                AvailableCommands.SHELTER_INFO.getDescription(),
                AvailableCommands.SHELTER_INFO.isTopLevel(),
                EnumSet.of(CurrentMenu.MAIN)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {
        userService.changeCurrentMenu(user, CurrentMenu.SHELTER_INFO);
    }
}
