package pro.sky.telegrambotshelter.configuration.messages;

import com.pengrad.telegrambot.model.BotCommand;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;

import java.util.Arrays;
import java.util.List;

public class CommandResponseMessages {
    public static final String START_RESPONSE_MSG = """
            ПРИВЕТСТВУЕМ, %s!
            Для доступа к справке введите /help
            """;
    public static final String HELP_RESPONSE_MSG = createHelpResponse();
    public static final String UNSUPPORTED_RESPONSE_MSG = "Команда не поддерживается";

    private static String createHelpResponse() {
        List<ExecutableBotCommand> list = Arrays.stream(AvailableCommands.values()).map(AvailableCommands::getCommand).toList();
        StringBuilder sb = new StringBuilder("Список доступных команд:\n");
        for (BotCommand botCommand : list) {
            sb.append(botCommand.command())
                    .append(" - ")
                    .append(botCommand.description())
                    .append("\n");
        }
        return sb.toString();
    }
}
