package pro.sky.telegrambotshelter.configuration.messages;

import pro.sky.telegrambotshelter.model.enums.BotCommands;
import pro.sky.telegrambotshelter.model.enums.BotMenus;

import java.util.Arrays;
import java.util.List;

public class CommandResponseMessages {
    public static final String START_RESPONSE_MSG = """
            ПРИВЕТСТВУЕМ, %s!
            """;
    public static final String HELP_RESPONSE_MSG = createListOfAvailableCommands();
    public static final String UNSUPPORTED_RESPONSE_MSG = "Команда не поддерживается";

    private static String createListOfCommands() {
        List<com.pengrad.telegrambot.model.BotCommand> list = Arrays.stream(BotCommands.values()).map(BotCommands::getCommand).toList();
        StringBuilder sb = new StringBuilder();
        for (com.pengrad.telegrambot.model.BotCommand botCommand : list) {
            sb.append(botCommand.command())
                    .append(" - ")
                    .append(botCommand.description())
                    .append("\n");
        }
        return sb.toString();
    }

    public static String createListOfAvailableCommands() {
        List<com.pengrad.telegrambot.model.BotCommand> list = Arrays.stream(BotCommands.values()).map(BotCommands::getCommand).toList();
        StringBuilder sb = new StringBuilder("Список доступных команд:\n");
        for (com.pengrad.telegrambot.model.BotCommand botCommand : list) {
            for (BotCommands commands: BotMenus.currentMenu.availableCommands) {
                if (botCommand.equals(commands.getCommand())) {
                    sb.append(botCommand.command())
                            .append(" - ")
                            .append(botCommand.description())
                            .append("\n");
                }
            }
        }
        return sb.toString();
    }
}
