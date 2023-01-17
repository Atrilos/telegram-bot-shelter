package pro.sky.telegrambotshelter.model.enums;

import com.pengrad.telegrambot.model.BotCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pro.sky.telegrambotshelter.model.commands.*;

@RequiredArgsConstructor
@Getter
public enum AvailableCommands {
    START("/start",
            new BotCommand("/start", "Приветствие пользователя"),
            new StartCommandHandler()
    ),
    HELP("/help",
            new BotCommand("/help", "Детальное описание команд"),
            new HelpCommandHandler()
    ),
    ADOPT("/adopt",
            new BotCommand("/adopt", "Взять собаку из приюта"),
            new AdoptCommandHandler()
    ),
    SHELTER_INFO("/shelter_info",
            new BotCommand("/shelter_info", "Узнать информацию о приюте"),
            new ShelterInfoCommandHandler()
    ),
    REPORT("/report",
            new BotCommand("/report", "Прислать отчет о питомце"),
            new ReportCommandHandler()
    ),
    CALL_STAFF("/call_staff",
            new BotCommand("/call_staff", "Позвать волонтера"),
            new CallStaffCommandHandler()
    );
    private final String value;
    private final BotCommand command;
    private final CommandHandler commandHandler;

    public static AvailableCommands fromString(String command) {

        for (AvailableCommands c : AvailableCommands.values())
            if (c.value.equals(command))
                return c;

        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
