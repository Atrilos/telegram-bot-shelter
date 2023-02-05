package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.model.enums.ShelterType;
import pro.sky.telegrambotshelter.service.UserService;

import java.util.EnumSet;

import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.CAT_SHELTER_SELECTED;

@Component
public class CatShelterCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public CatShelterCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.CAT_SHELTER.getCommand(),
                AvailableCommands.CAT_SHELTER.getDescription(),
                AvailableCommands.CAT_SHELTER.isTopLevel(),
                EnumSet.of(CurrentMenu.SELECT_SHELTER)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        addAlias(AvailableCommands.CAT_SHELTER.getDescription());
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, CAT_SHELTER_SELECTED);
        bot.execute(message);
        userService.changeCurrentShelterType(user, ShelterType.CAT);
        userService.changeCurrentMenu(user, CurrentMenu.MAIN);
    }
}
