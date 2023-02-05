package pro.sky.telegrambotshelter.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static pro.sky.telegrambotshelter.configuration.UIstrings.CommandDescriptions.*;

/**
 * Enum класс, содержащий доступные боту команды
 */
@RequiredArgsConstructor
@Getter
public enum AvailableCommands {
    START("/start", START_DESC, true),
    HELP("/help", HELP_DESC, false),
    ADOPT("/adopt", ADOPT_DESC, true),
    SHELTER_INFO("/shelter_info", SHELTER_INFO_DESC, true),
    REPORT("/report", SEND_REPORT_DESC, true),
    CALL_STAFF("/call_staff", CALL_STAFF_DESC, true),
    ABOUT_SHELTER("/about_shelter", ABOUT_DESC, false),
    SHELTER_ADDRESS("/shelter_address", ADDRESS_DESC, false),
    SAFETY_RULES("/safety_rules", SAFETY_RULES_DESC, false),
    MEET_ANIMAL("/meet_animal", MEET_ANIMAL_DESC, false),
    PAPERS("/papers", PAPERS_DESC, false),
    TRANSPORT_ANIMAL("/transport_animal", TRANSPORT_ANIMAL_DESC, false),
    PUPPY_HOME("/puppy_home", PUP_HOME_DESC, false),
    DOG_HOME("/dog_home", DOG_HOME_DESC, false),
    CAT_HOME("/cat_home", CAT_HOME_DESC, false),
    DISABLED_DOG_HOME("/disabled_dog_home", DISABLED_DOG_HOME_DESC, false),
    COMMUNICATION("/communication", COMMUNICATION_DESC, false),
    DOG_HANDLERS("/dog_handlers", DOG_HANDLERS_DESC, false),
    REFUSAL_CAUSE("/refusal_cause", REFUSAL_CAUSE_DESC, false),
    STORE_CONTACT_INFO("/store_contact_info", STORE_CONTACT_INFO_DESC, false),
    CAT_SHELTER("/cat_shelter", CAT_SHELTER_DESC, false),
    DOG_SHELTER("/dog_shelter", DOG_SHELTER_DESC, false);
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
