package pro.sky.telegrambotshelter.model.commands.adopt;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.PetService;
import pro.sky.telegrambotshelter.service.ShelterService;

import java.util.EnumSet;

@Component
public class SelectDogCommand extends ExecutableBotCommand {

    private final ShelterService shelterService;
    private final PetService petService;
    private final TelegramCommandBot bot;

    public SelectDogCommand(ShelterService shelterService, PetService petService, TelegramCommandBot bot) {
        super(AvailableCommands.SELECT_DOG.getCommand(),
                AvailableCommands.SELECT_DOG.getDescription(),
                AvailableCommands.SELECT_DOG.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION)
        );
        this.shelterService = shelterService;
        this.petService = petService;
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();

    }
}
