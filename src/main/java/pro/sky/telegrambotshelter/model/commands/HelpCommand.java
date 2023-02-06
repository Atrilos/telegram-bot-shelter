package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;

import java.util.EnumSet;

@Component
public class HelpCommand extends ExecutableBotCommand {

    private final TelegramCommandBot bot;

    public HelpCommand(TelegramCommandBot bot) {
        super(AvailableCommands.HELP.getCommand(),
                AvailableCommands.HELP.getDescription(),
                AvailableCommands.HELP.isTopLevel(),
                EnumSet.allOf(CurrentMenu.class)
        );
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, UIstrings.HELP_RESPONSE));
    }
}
