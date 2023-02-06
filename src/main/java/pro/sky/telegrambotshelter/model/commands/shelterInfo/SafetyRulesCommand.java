package pro.sky.telegrambotshelter.model.commands.shelterInfo;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.Shelter;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;
import pro.sky.telegrambotshelter.model.commands.ShelterInfoCommand;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.ShelterService;

import java.util.EnumSet;


@Component
public class SafetyRulesCommand extends ExecutableBotCommand {

    private final ShelterService shelterService;
    private final TelegramCommandBot bot;

    public SafetyRulesCommand(ShelterService shelterService, TelegramCommandBot bot) {
        super(AvailableCommands.SAFETY_RULES.getCommand(),
                AvailableCommands.SAFETY_RULES.getDescription(),
                AvailableCommands.SAFETY_RULES.isTopLevel(),
                EnumSet.of(CurrentMenu.SHELTER_INFO)
        );
        this.shelterService = shelterService;
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        Shelter shelter = shelterService.getShelter(user);
        SendMessage message = new SendMessage(chatId, shelter.getSafetyInstructions());
        message.replyMarkup(ShelterInfoCommand.createReplyKeyboard());
        bot.execute(message);
    }
}
