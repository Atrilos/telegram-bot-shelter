package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Абстрактный класс, расширяющий {@link com.pengrad.telegrambot.model.BotCommand BotCommand}.
 * Содержит в себе дополнительный функционал, по обработке полученной команды, также проверяет полученную команду на
 * соответствие требованиям telegram api (только строчные буквы, не более 32 знаков, команда не должна быть пустой или null)
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class ExecutableBotCommand extends BotCommand {
    private static final String COMMAND_INIT_CHARACTER = "/";
    private static final int COMMAND_MAX_LENGTH = 32;
    private final Set<String> commandAliases = new HashSet<>();
    /**
     * true - команда верхнего уровня, false - команда подменю
     */
    private final boolean topLevel;
    /**
     * Список поддерживаемых подменю
     */
    private final EnumSet<CurrentMenu> supportedMenuList;

    /**
     * Конструктор класса. Содержит проверки на соответствие требованиям telegram api
     *
     * @param command           текстовое значение команды
     * @param description       текстовое описание команды
     * @param topLevel          является ли командой верхнего уровня или частью подменю
     * @param supportedMenuList список подменю, в которых является допустимой командой
     */
    public ExecutableBotCommand(@NotNull String command, String description, boolean topLevel, EnumSet<CurrentMenu> supportedMenuList) {
        super(command.toLowerCase(), description);
        this.topLevel = topLevel;
        this.supportedMenuList = supportedMenuList;
        if (command.isEmpty()) {
            throw new IllegalArgumentException("command cannot be null or empty");
        }
        if (command.length() > COMMAND_MAX_LENGTH) {
            throw new IllegalArgumentException("command cannot be longer than " + COMMAND_MAX_LENGTH + " (including " + COMMAND_INIT_CHARACTER + ")");
        }
        if (!command.matches("[a-zA-Z0-9_/]*")) {
            throw new IllegalArgumentException("command can contain only lowercase English letters, digits and underscores");
        }
        addAlias(command.toLowerCase());
        addAlias(description);
    }

    /**
     * Метод, выполняющийся для обработки поступившей команды
     *
     * @param update полученное обновление
     * @param user   пользователь, обратившийся к боту
     */
    public abstract void execute(Update update, User user);

    /**
     * Метод, добавляющий слова или фразы, которые должны после парсинга обрабатываться данной командой
     *
     * @param alias слово или фраза, ссылающаяся на данную команду
     */
    public void addAlias(String alias) {
        commandAliases.add(alias);
    }

    /**
     * Метод, добавляющий несколько слов или фраз, которые должны после парсинга обрабатываться данной командой
     *
     * @param aliases коллекция слов или фраз, ссылающихся на данную команду
     */
    public void addAllAliases(Collection<? extends String> aliases) {
        commandAliases.addAll(aliases);
    }

    /**
     * Определяет подходит ли данная команда при использовании данного текста сообщения и при нахождении пользователя
     * в данном подменю
     *
     * @param message     данное сообщение
     * @param currentMenu данное подменю
     * @return true - команда может использоваться, false - в ином случае
     */
    public boolean isSupported(String message, CurrentMenu currentMenu) {
        if (!supportedMenuList.contains(currentMenu)) {
            return false;
        } else return commandAliases.contains(message);
    }

}
