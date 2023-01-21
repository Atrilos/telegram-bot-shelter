package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.service.UserService;


public abstract class ExecutableBotCommand extends BotCommand {
    private static final String COMMAND_INIT_CHARACTER = "/";
    private static final int COMMAND_MAX_LENGTH = 32;

    public ExecutableBotCommand(String command, String description) {
        super(command, description);
        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("command cannot be null or empty");
        }
        if (command.length() > COMMAND_MAX_LENGTH) {
            throw new IllegalArgumentException("command cannot be longer than " + COMMAND_MAX_LENGTH + " (including " + COMMAND_INIT_CHARACTER + ")");
        }
        if (!command.equals(command.toLowerCase())) {
            throw new IllegalArgumentException("command cannot contain upper case letters");
        }
    }

    public abstract void execute(TelegramBot bot, Update update, User user, UserService userService);
}
