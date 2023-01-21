package pro.sky.telegrambotshelter.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static pro.sky.telegrambotshelter.model.enums.AvailableCommands.*;

@Getter
@RequiredArgsConstructor
public enum CurrentMenu {
    MAIN(List.of(ADOPT, AvailableCommands.REPORT, CALL_STAFF)),
    SHELTER_INFO(List.of(ABOUT_SHELTER, SHELTER_ADDRESS, SAFETY_RULES, TO_MAIN_MENU, CALL_STAFF)),
    ADOPTION(List.of(MEET_DOG, PAPERS, TRANSPORT_ANIMAL, PUPPY_HOME, DOG_HOME, DISABLED_DOG_HOME, COMMUNICATION, DOG_HANDLERS, REFUSAL_CAUSE, STORE_CONTACT_INFO, TO_MAIN_MENU, CALL_STAFF)),
    REPORT(List.of(TO_MAIN_MENU, CALL_STAFF));

    private final List<AvailableCommands> availableCommands;

}
