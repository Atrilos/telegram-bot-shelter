package pro.sky.telegrambotshelter.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pro.sky.telegrambotshelter.model.commands.*;

@RequiredArgsConstructor
@Getter
public enum AvailableCommands {
    START("/start",
            new StartCommand("/start", "Приветствие пользователя")
    ),
    HELP("/help",
            new HelpCommand("/help", "Детальное описание команд")
    ),
    ADOPT("/adopt",
            new AdoptCommand("/adopt", "Взять собаку из приюта")
    ),
    SHELTER_INFO("/shelter_info",
            new ShelterInfoCommand("/shelter_info", "Узнать информацию о приюте")
    ),
    REPORT("/report",
            new ReportCommand("/report", "Прислать отчет о питомце")
    ),
    CALL_STAFF("/call_staff",
            new CallStaffCommand("/call_staff", "Позвать волонтера")
    );
    private final String value;
    private final ExecutableBotCommand command;

    @Override
    public String toString() {
        return value;
    }
}
