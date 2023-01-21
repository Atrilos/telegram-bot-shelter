package pro.sky.telegrambotshelter.model.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeDefault;
import com.pengrad.telegrambot.request.SetMyCommands;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;

import java.util.HashMap;
import java.util.Map;

public class TelegramCommandBot extends TelegramBot {
    private final Map<String, ExecutableBotCommand> commandRegistry = new HashMap<>();

    public TelegramCommandBot(String botToken) {
        super(botToken);
    }

    public boolean registerCommand(ExecutableBotCommand executableBotCommand) {
        String command = executableBotCommand.command();
        if (commandRegistry.containsKey(command)) {
            return false;
        }
        commandRegistry.put(command, executableBotCommand);
        return true;
    }

    public boolean deregisterCommand(ExecutableBotCommand executableBotCommand) {
        String command = executableBotCommand.command();
        if (!commandRegistry.containsKey(command)) {
            return false;
        }
        commandRegistry.remove(command);
        return true;
    }

    public boolean deregisterCommand(String command) {
        if (!commandRegistry.containsKey(command)) {
            return false;
        }
        commandRegistry.remove(command);
        return true;
    }

    public void createMainMenu() {
        BotCommand[] availableBotCommands = commandRegistry.values().toArray(BotCommand[]::new);
        execute(
                new SetMyCommands(availableBotCommands)
                        .scope(new BotCommandScopeDefault()));
    }

    public ExecutableBotCommand getCommand(String command) {
        return commandRegistry.get(command);
    }
}
