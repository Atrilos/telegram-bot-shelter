package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;

import java.util.EnumSet;

import static pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages.START_RESPONSE_MSG;

@Component
public class StartCommand extends ExecutableBotCommand {
    private final TelegramCommandBot bot;


    public StartCommand(TelegramCommandBot bot) {
        super(AvailableCommands.START.getCommand(),
                AvailableCommands.START.getDescription(),
                AvailableCommands.START.isTopLevel(),
                EnumSet.of(CurrentMenu.MAIN)
        );
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        String responseMsg = START_RESPONSE_MSG.formatted(update.message().from().firstName());
        bot.execute(new SendMessage(chatId, responseMsg));
    }
}
