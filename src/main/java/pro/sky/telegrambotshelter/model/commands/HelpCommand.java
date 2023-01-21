package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.service.UserService;

public class HelpCommand extends ExecutableBotCommand {
    public HelpCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(TelegramBot bot, Update update, User user, UserService userService) {
        Long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, CommandResponseMessages.HELP_RESPONSE_MSG));
    }
}
