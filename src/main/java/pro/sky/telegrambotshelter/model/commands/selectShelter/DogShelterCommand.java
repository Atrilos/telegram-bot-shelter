package pro.sky.telegrambotshelter.model.commands.selectShelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;
import pro.sky.telegrambotshelter.model.commands.StartCommand;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.model.enums.ShelterType;
import pro.sky.telegrambotshelter.service.UserService;

import java.util.EnumSet;

import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.DOG_SHELTER_SELECTED;

@Component
public class DogShelterCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public DogShelterCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.DOG_SHELTER.getCommand(),
                AvailableCommands.DOG_SHELTER.getDescription(),
                AvailableCommands.DOG_SHELTER.isTopLevel(),
                EnumSet.of(CurrentMenu.SELECT_SHELTER)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        userService.changeCurrentShelterType(user, ShelterType.DOG);
        userService.changeCurrentMenu(user, CurrentMenu.MAIN);
        SendMessage message = new SendMessage(chatId, DOG_SHELTER_SELECTED);
        ReplyKeyboardMarkup replyKeyboardMarkup = StartCommand.createReplyKeyboardShelterKnown();
        message.replyMarkup(replyKeyboardMarkup);
        bot.execute(message);
    }
}
