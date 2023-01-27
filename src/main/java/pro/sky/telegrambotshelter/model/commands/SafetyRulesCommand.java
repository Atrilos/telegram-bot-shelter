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
public class SafetyRulesCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;
    private final String safetyRules = "safetyRules";

    public SafetyRulesCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.SAFETY_RULES.getCommand(),
                AvailableCommands.SAFETY_RULES.getDescription(),
                AvailableCommands.SAFETY_RULES.isTopLevel(),
                EnumSet.of(CurrentMenu.SHELTER_INFO)
        );
        this.userService = userService;
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        addAllAliases(List.of(AvailableCommands.SAFETY_RULES.getDescription(), AvailableCommands.SAFETY_RULES.getDescription()));
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        SendMessage message = new SendMessage(chatId, safetyRules);
        message.replyMarkup(ShelterInfoCommand.createReplyKeyboard());
        bot.execute(message);
    }
}
