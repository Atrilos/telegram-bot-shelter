package pro.sky.telegrambotshelter.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum BotMenus
{
    MAIN(new ArrayList<>(List.of(BotCommands.ADOPT, BotCommands.REPORT, BotCommands.CALL_STAFF))),
    SHELTER_INFO(new ArrayList<>(List.of(BotCommands.ABOUT_SHELTER, BotCommands.SHELTER_ADDRESS, BotCommands.SAFETY_RULES, BotCommands.TO_MAIN_MENU, BotCommands.CALL_STAFF))),
    ADOPTION(new ArrayList<>(List.of(BotCommands.MEET_DOG, BotCommands.PAPERS, BotCommands.TRANSPORT_ANIMAL, BotCommands.PUPPY_HOME, BotCommands.DOG_HOME, BotCommands.DISABLED_DOG_HOME, BotCommands.COMMUNICATION, BotCommands.DOG_HANDLERS, BotCommands.REFUSAL_CAUSE, BotCommands.STORE_CONTACT_INFO, BotCommands.TO_MAIN_MENU, BotCommands.CALL_STAFF))),
    REPORT(new ArrayList<>(List.of(BotCommands.TO_MAIN_MENU, BotCommands.CALL_STAFF)));

    public static BotMenus currentMenu;
    public final List<BotCommands> availableCommands;

    BotMenus(List<BotCommands> availableCommands)
    {
        this.availableCommands = availableCommands;
    }
}
