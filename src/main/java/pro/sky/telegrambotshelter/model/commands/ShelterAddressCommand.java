package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;

import java.util.EnumSet;
import java.util.List;

@Component
public class ShelterAddressCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;
    private final String shelterAddress = "shelterAddress";

    public ShelterAddressCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.SHELTER_ADDRESS.getCommand(),
                AvailableCommands.SHELTER_ADDRESS.getDescription(),
                AvailableCommands.SHELTER_ADDRESS.isTopLevel(),
                EnumSet.of(CurrentMenu.SHELTER_INFO)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        addAllAliases(List.of("Расписание работы приюта, адрес, схема проезда", "Расписание работы приюта, адрес, схема проезда"));
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, shelterAddress);
        message.replyMarkup(ShelterInfoCommand.createReplyKeyboard());
        bot.execute(message);
    }
}
