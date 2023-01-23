package pro.sky.telegrambotshelter.model.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeDefault;
import com.pengrad.telegrambot.request.SetMyCommands;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, наследующий от стандартного {@link com.pengrad.telegrambot.TelegramBot TelegramBot} и расширяющий его для
 * более удобного использования команд (их парсинга, а также регистрации меню).
 */
public class TelegramCommandBot extends TelegramBot {
    /**
     * Список всех доступных команд в виде мапы, где ключами выступают текстовые команды, а значениями
     * {@link pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand ExecutableBotCommand}
     */
    private final Map<String, ExecutableBotCommand> commandRegistry = new HashMap<>();

    public TelegramCommandBot(String botToken) {
        super(botToken);
    }

    /**
     * Метод для регистрации новых команд
     * @param executableBotCommand новая команда
     * @return true, если команда была успешно добавлена, false - если команда уже существует
     */
    public boolean registerCommand(ExecutableBotCommand executableBotCommand) {
        String command = executableBotCommand.command();
        if (commandRegistry.containsKey(command)) {
            return false;
        }
        commandRegistry.put(command, executableBotCommand);
        return true;
    }

    /**
     * Метод для удаления существующих команд
     * @param executableBotCommand команда, которую удаляем
     * @return true, если команда была успешно удалена, false - если команда не существовала
     */
    public boolean deregisterCommand(ExecutableBotCommand executableBotCommand) {
        String command = executableBotCommand.command();
        if (!commandRegistry.containsKey(command)) {
            return false;
        }
        commandRegistry.remove(command);
        return true;
    }

    /**
     * Метод для удаления существующих команд
     * @param command текстовая команда, которую удаляем
     * @return true, если команда была успешно удалена, false - если команда не существовала
     */
    public boolean deregisterCommand(String command) {
        if (!commandRegistry.containsKey(command)) {
            return false;
        }
        commandRegistry.remove(command);
        return true;
    }

    /**
     * Метод для создания главного меню из зарегистрированных команд
     */
    public void createMainMenu() {
        BotCommand[] availableBotCommands = commandRegistry.values().toArray(BotCommand[]::new);
        execute(
                new SetMyCommands(availableBotCommands)
                        .scope(new BotCommandScopeDefault()));
    }

    /**
     * Получение команды по ее текстовому значению
     * @param command текстовое значение команды
     * @return команда в виде {@link pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand ExecutableBotCommand}
     */
    public ExecutableBotCommand getCommand(String command) {
        return commandRegistry.get(command);
    }
}
