package pro.sky.telegrambotshelter.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum класс, содержащий доступные боту команды
 */
@RequiredArgsConstructor
@Getter
public enum AvailableCommands {
    START("/start", "Приветствие пользователя", true),
    HELP("/help", "Детальное описание команд", true),
    ADOPT("/adopt", "Взять собаку из приюта", true),
    SHELTER_INFO("/shelter_info", "Узнать информацию о приюте", true),
    REPORT("/report", "Прислать отчет о питомце", true),
    CALL_STAFF("/call_staff", "Позвать волонтера", true),
    ABOUT_SHELTER("/about_shelter", "О приюте", false),
    SHELTER_ADDRESS("/shelter_address", "Расписание работы приюта, адрес, схема проезда", false),
    SAFETY_RULES("/safety_rules", "Техника безопасности на территории приюта", false),
    MEET_DOG("/meet_dog", "Правила знакомства с собакой", false),
    PAPERS("/papers", "Список документов, чтобы взять собаку из приюта", false),
    TRANSPORT_ANIMAL("/transport_animal", "Рекомендации по транспортировке животного", false),
    PUPPY_HOME("/puppy_home", "Рекомендации по обустройству дома для щенка", false),
    DOG_HOME("/dog_home", "Рекомендации по обустройству дома для взрослой собаки", false),
    DISABLED_DOG_HOME("/disabled_dog_home",
            "Рекомендации по обустройству дома для собаки с ограниченными возможностями", false),
    COMMUNICATION("/communication",
            "Советы кинолога по первичному общению с собакой", false),
    DOG_HANDLERS("/dog_handlers",
            "Рекомендации по проверенным кинологам для дальнейшего обращения к ним", false),
    REFUSAL_CAUSE("/refusal_cause",
            "Список причин, по которым вам могут отказать и не дать забрать собаку из приюта", false),
    STORE_CONTACT_INFO("/store_contact_info",
            "оставить свои контактные данные для связи", false),

    SEND_REPORT("/send_report", "Отправить отчет о питомце", false);
    /**
     * Текстовое значение команды
     */
    private final String command;
    /**
     * Описание команды
     */
    private final String description;
    /**
     * true - команда верхнего уровня, false - команда подменю
     */
    private final boolean topLevel;

}
