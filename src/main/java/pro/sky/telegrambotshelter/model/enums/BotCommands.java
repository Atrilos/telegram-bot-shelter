package pro.sky.telegrambotshelter.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pro.sky.telegrambotshelter.model.commands.*;


@RequiredArgsConstructor
@Getter
public enum BotCommands
{
    START("/start",
            new com.pengrad.telegrambot.model.BotCommand("/start", "Приветствие пользователя"),
            new StartCommandHandler()
    ),
//    HELP("/help",
//            new com.pengrad.telegrambot.model.BotCommand("/help", "Детальное описание команд"),
//            new HelpCommandHandler()
//    ),
    SHELTER_INFO("/shelter_info",
            new com.pengrad.telegrambot.model.BotCommand("/shelter_info", "Узнать информацию о приюте"),
            new ShelterInfoCommandHandler()
    ),
    ADOPT("/adopt",
            new com.pengrad.telegrambot.model.BotCommand("/adopt", "Взять собаку из приюта"),
            new AdoptCommandHandler()
    ),
    REPORT("/report",
            new com.pengrad.telegrambot.model.BotCommand("/report", "Прислать отчет о питомце"),
            new ReportCommandHandler()
    ),
    CALL_STAFF("/call_staff",
            new com.pengrad.telegrambot.model.BotCommand("/call_staff", "Позвать волонтера"),
            new CallStaffCommandHandler()
    ),

    ABOUT_SHELTER("/about_shelter",
            new com.pengrad.telegrambot.model.BotCommand("/about_shelter", "О приюте."),
            new AboutShelterCommandHandler()
    ),
    SHELTER_ADDRESS("/shelter_address",
            new com.pengrad.telegrambot.model.BotCommand("/shelter_address", "Расписание работы приюта, адрес, схема проезда"),
            new ShelterAddressCommandHandler()
    ),
    SAFETY_RULES("/safety_rules",
            new com.pengrad.telegrambot.model.BotCommand("/safety_rules", "Техника безопасности на территории приюта"),
            new SafetyRulesCommandHandler()
    ),

    MEET_DOG("/meet_dog",
            new com.pengrad.telegrambot.model.BotCommand("/meet_dog", "Правила знакомства с собакой"),
            new MeetDogCommandHandler()
    ),
    PAPERS("/papers",
            new com.pengrad.telegrambot.model.BotCommand("/papers", "Список документов, чтобы взять собаку из приюта"),
            new PapersCommandHandler()
    ),
    TRANSPORT_ANIMAL("/transport_animal",
            new com.pengrad.telegrambot.model.BotCommand("/transport_animal", "Рекомендации по транспортировке животного"),
            new TransportAnimalCommandHandler()
    ),
    PUPPY_HOME("/puppy_home",
            new com.pengrad.telegrambot.model.BotCommand("/puppy_home", "Рекомендации по обустройству дома для щенка"),
            new PuppyHomeCommandHandler()
    ),
    DOG_HOME("/dog_home",
            new com.pengrad.telegrambot.model.BotCommand("/dog_home", "Рекомендации по обустройству дома для взрослой собаки"),
            new DogHomeCommandHandler()
    ),
    DISABLED_DOG_HOME("/disabled_dog_home",
            new com.pengrad.telegrambot.model.BotCommand("/disabled_dog_home", "Рекомендации по обустройству дома для собаки с ограниченными возможностями"),
            new DisabledDogHomeCommandHandler()
    ),
    COMMUNICATION("/communication",
            new com.pengrad.telegrambot.model.BotCommand("/communication", "Советы кинолога по первичному общению с собакой"),
            new CommunicationCommandHandler()
    ),
    DOG_HANDLERS("/dog_handlers",
            new com.pengrad.telegrambot.model.BotCommand("/dog_handlers", "Рекомендации по проверенным кинологам для дальнейшего обращения к ним"),
            new DogHandlersCommandHandler()
    ),
    REFUSAL_CAUSE("/refusal_cause",
            new com.pengrad.telegrambot.model.BotCommand("/refusal_cause", "Список причин, по которым вам могут отказать и не дать забрать собаку из приюта"),
            new RefusalCauseCommandHandler()
    ),
    STORE_CONTACT_INFO("/store_contact_info",
            new com.pengrad.telegrambot.model.BotCommand("/store_contact_info", "оставить саои контактные данные для связи"),
            new StoreContactInfoCommandHandler()
    ),

    SEND_REPORT("/send_report",
            new com.pengrad.telegrambot.model.BotCommand("/send_report", "Отправить отчет о питомце"),
            new SendReportCommandHandler()
    ),

    TO_MAIN_MENU("/to_main_menu",
            new com.pengrad.telegrambot.model.BotCommand("/to_main_menu", "В главное меню"),
            new ToMainMenuCommandHandler()
    );
    private final String value;
    private final com.pengrad.telegrambot.model.BotCommand command;
    private final CommandHandler commandHandler;

    public static BotCommands fromString(String command) {

        for (BotCommands c : BotCommands.values())
            if (c.value.equals(command))
                return c;

        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
