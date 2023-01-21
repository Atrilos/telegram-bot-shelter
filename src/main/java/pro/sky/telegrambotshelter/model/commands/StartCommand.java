package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.service.UserService;

import static pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages.START_RESPONSE_MSG;

public class StartCommand extends ExecutableBotCommand {

    public StartCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(TelegramBot bot, Update update, User user, UserService userService) {
        Long chatId = update.message().chat().id();
        String responseMsg = START_RESPONSE_MSG.formatted(update.message().from().firstName());
        bot.execute(new SendMessage(chatId, responseMsg));
    }
}
