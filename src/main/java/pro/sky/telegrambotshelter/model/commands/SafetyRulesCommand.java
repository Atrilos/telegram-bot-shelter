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
public class SafetyRulesCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public SafetyRulesCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.SAFETY_RULES.getCommand(),
                AvailableCommands.SAFETY_RULES.getDescription(),
                AvailableCommands.SAFETY_RULES.isTopLevel(),
                EnumSet.of(CurrentMenu.SHELTER_INFO)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {

    }
}
