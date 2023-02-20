package pro.sky.telegrambotshelter.model.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeDefault;
import com.pengrad.telegrambot.request.SetMyCommands;
import lombok.Getter;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, наследующий от стандартного {@link com.pengrad.telegrambot.TelegramBot TelegramBot} и расширяющий его для
 * более удобного использования команд (их парсинга, а также регистрации меню).
 */
@Getter
public class TelegramCommandBot extends TelegramBot {
    /**
     * Список всех доступных команд в виде сета, где значениями выступают
     * {@link pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand ExecutableBotCommand}
     */
    private final Set<BotCommand> commandRegistry = new HashSet<>();

    private final boolean testMode = true;

    public TelegramCommandBot(String botToken) {
        super(botToken);
    }

    /**
     * Метод для регистрации новых команд
     *
     * @param availableCommand команда для добавления на основе enum
     * @return true, если команда была успешно добавлена, false - если команда уже существует
     */
    public boolean registerCommand(AvailableCommands availableCommand) {
        BotCommand botCommand = new BotCommand(availableCommand.getCommand(), availableCommand.getDescription());
        return commandRegistry.add(botCommand);
    }

    /**
     * Метод для создания главного меню из зарегистрированных команд
     */
    public void createMainMenu() {
        BotCommand[] availableBotCommands =
                commandRegistry.toArray(BotCommand[]::new);
        execute(
                new SetMyCommands(availableBotCommands)
                        .scope(new BotCommandScopeDefault()));
    }
}
