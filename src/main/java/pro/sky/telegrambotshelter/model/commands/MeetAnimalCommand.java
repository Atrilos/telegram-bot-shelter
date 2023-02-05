package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.Shelter;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;

import java.util.EnumSet;


@Component
public class MeetAnimalCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public MeetAnimalCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.MEET_ANIMAL.getCommand(),
                AvailableCommands.MEET_ANIMAL.getDescription(),
                AvailableCommands.MEET_ANIMAL.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        Shelter shelter = userService.getShelter(user);
        SendMessage message = new SendMessage(chatId, shelter.getMeetingRules());
        message.replyMarkup(AdoptCommand.createReplyKeyboard(user));
        bot.execute(message);
    }
}
