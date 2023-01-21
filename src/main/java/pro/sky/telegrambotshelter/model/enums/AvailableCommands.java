package pro.sky.telegrambotshelter.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pro.sky.telegrambotshelter.model.commands.*;

@RequiredArgsConstructor
@Getter
public enum AvailableCommands {
    START("/start",
            new StartCommand("/start", "Приветствие пользователя"), false
    ),
    HELP("/help",
            new HelpCommand("/help", "Детальное описание команд"), false
    ),
    ADOPT("/adopt",
            new AdoptCommand("/adopt", "Взять собаку из приюта"), false
    ),
    SHELTER_INFO("/shelter_info",
            new ShelterInfoCommand("/shelter_info", "Узнать информацию о приюте"), false
    ),
    REPORT("/report",
            new ReportCommand("/report", "Прислать отчет о питомце"), false
    ),
    CALL_STAFF("/call_staff",
            new CallStaffCommand("/call_staff", "Позвать волонтера"), true
    ),
    ABOUT_SHELTER("/about_shelter",
            new AboutShelterCommand("/about_shelter", "О приюте."), true
    ),
    SHELTER_ADDRESS("/shelter_address",
            new ShelterAddressCommand("/shelter_address",
                    "Расписание работы приюта, адрес, схема проезда"), true
    ),
    SAFETY_RULES("/safety_rules",
            new SafetyRulesCommand("/safety_rules",
                    "Техника безопасности на территории приюта"), true
    ),
    MEET_DOG("/meet_dog",
            new MeetDogCommand("/meet_dog", "Правила знакомства с собакой"), true
    ),
    PAPERS("/papers",
            new PapersCommand("/papers", "Список документов, чтобы взять собаку из приюта"), true
    ),
    TRANSPORT_ANIMAL("/transport_animal",
            new TransportAnimalCommand("/transport_animal",
                    "Рекомендации по транспортировке животного"), true
    ),
    PUPPY_HOME("/puppy_home",
            new PuppyHomeCommand("/puppy_home",
                    "Рекомендации по обустройству дома для щенка"), true
    ),
    DOG_HOME("/dog_home",
            new DogHomeCommand("/dog_home",
                    "Рекомендации по обустройству дома для взрослой собаки"), true
    ),
    DISABLED_DOG_HOME("/disabled_dog_home",
            new DisabledDogHomeCommand("/disabled_dog_home",
                    "Рекомендации по обустройству дома для собаки с ограниченными возможностями"), true
    ),
    COMMUNICATION("/communication",
            new CommunicationCommand("/communication",
                    "Советы кинолога по первичному общению с собакой"), true
    ),
    DOG_HANDLERS("/dog_handlers",
            new DogHandlersCommand("/dog_handlers",
                    "Рекомендации по проверенным кинологам для дальнейшего обращения к ним"), true
    ),
    REFUSAL_CAUSE("/refusal_cause",
            new RefusalCauseCommand("/refusal_cause",
                    "Список причин, по которым вам могут отказать и не дать забрать собаку из приюта"), true
    ),
    STORE_CONTACT_INFO("/store_contact_info",
            new StoreContactInfoCommand("/store_contact_info",
                    "оставить свои контактные данные для связи"), true
    ),

    SEND_REPORT("/send_report",
            new SendReportCommand("/send_report", "Отправить отчет о питомце"), true
    ),

    TO_MAIN_MENU("/to_main_menu",
            new ToMainMenuCommand("/to_main_menu", "В главное меню"), true
    );
    private final String value;
    private final ExecutableBotCommand command;
    private final Boolean isNested;

    @Override
    public String toString() {
        return value;
    }
}
