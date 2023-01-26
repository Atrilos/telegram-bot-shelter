package pro.sky.telegrambotshelter.configuration.messages;

import pro.sky.telegrambotshelter.model.enums.AvailableCommands;

import java.util.Arrays;

/**
 * Класс, содержащий константы для текстовых ответов на запросы боту.
 */
public class CommandResponseMessages {
    public static final String START_RESPONSE_MSG = """
            ПРИВЕТСТВУЕМ, %s!
            """;
    public static final String UNSUPPORTED_RESPONSE_MSG = "Команда не поддерживается";

    public static final String HELP_RESPONSE_MSG = createListOfAvailableCommands();

    /**
     * Вспомогательный метод для формирования справки
     *
     * @return текст для справки /help
     */
    private static String createListOfAvailableCommands() {
        StringBuilder sb = new StringBuilder("Список доступных команд:\n");
        Arrays.stream(AvailableCommands.values()).forEach(command -> sb.append(command.getCommand())
                .append(" - ")
                .append(command.getDescription())
                .append("\n"));
        return sb.toString();
    }
}
