package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.Shelter;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.ShelterService;

import java.util.EnumSet;


@Component
public class PapersCommand extends ExecutableBotCommand {

    private final ShelterService shelterService;
    private final TelegramCommandBot bot;

    public PapersCommand(ShelterService shelterService, TelegramCommandBot bot) {
        super(AvailableCommands.PAPERS.getCommand(),
                AvailableCommands.PAPERS.getDescription(),
                AvailableCommands.PAPERS.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION)
        );
        this.shelterService = shelterService;
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        Shelter shelter = shelterService.getShelter(user);
        SendMessage message = new SendMessage(chatId, shelter.getPapers());
        message.replyMarkup(AdoptCommand.createReplyKeyboard(user));
        bot.execute(message);
    }
}
