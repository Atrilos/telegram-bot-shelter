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
public class SendReportCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public SendReportCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.SEND_REPORT.getCommand(),
                AvailableCommands.SEND_REPORT.getDescription(),
                AvailableCommands.SEND_REPORT.isTopLevel(),
                EnumSet.of(CurrentMenu.REPORT)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {

    }
}
