package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.service.UserService;

/**
 * Абстрактный класс, расширяющий {@link com.pengrad.telegrambot.model.BotCommand BotCommand}.
 * Содержит в себе дополнительный функционал, по обработке полученной команды, также проверяет полученную команду на
 * соответствие требованиям telegram api (только строчные буквы, не более 32 знаков, команда не должна быть пустой или null)
 */
public abstract class ExecutableBotCommand extends BotCommand {
    private static final String COMMAND_INIT_CHARACTER = "/";
    private static final int COMMAND_MAX_LENGTH = 32;

    /**
     * Конструктор класса. Содержит проверки на соответствие требованиям telegram api
     * @param command текстовое значение команды
     * @param description текстовое описание команды
     */
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

    /**
     * Метод, выполняющийся для обработки поступившей команды
     * @param bot использующийся телеграм бот
     * @param update полученное обновление
     * @param user пользователь, обратившийся к боту
     * @param userService сервис, для работы с классом {@link pro.sky.telegrambotshelter.model.User User}
     */
    public abstract void execute(TelegramBot bot, Update update, User user, UserService userService);
}
