package pro.sky.telegrambotshelter.model.commands.adopt;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.configuration.UIstrings.CommandDescriptions;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;

import java.util.EnumSet;

import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.CONTACT_ALREADY_IN_DB;
import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.SEND_YOUR_CONTACT_INFO;
import static pro.sky.telegrambotshelter.utils.KeyboardUtils.createKeyboard;
import static pro.sky.telegrambotshelter.utils.KeyboardUtils.createKeyboardButton;

@Component
public class StoreContactInfoCommand extends ExecutableBotCommand {

    private final TelegramCommandBot bot;

    public StoreContactInfoCommand(TelegramCommandBot bot) {
        super(AvailableCommands.STORE_CONTACT_INFO.getCommand(),
                AvailableCommands.STORE_CONTACT_INFO.getDescription(),
                AvailableCommands.STORE_CONTACT_INFO.isTopLevel(),
                EnumSet.of(CurrentMenu.ADOPTION)
        );
        this.bot = bot;
    }

    @Override
    public void execute(Update update, User user) {
        SendMessage message;
        if (user.getPhoneNumber() != null) {
            message = new SendMessage(user.getChatId(), CONTACT_ALREADY_IN_DB);
        } else {
            message = new SendMessage(user.getChatId(), AvailableCommands.STORE_CONTACT_INFO.getDescription());
            ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard();
            message.replyMarkup(replyKeyboardMarkup);
        }
        bot.execute(message);
    }

    private ReplyKeyboardMarkup createReplyKeyboard() {
        return createKeyboard(
                createKeyboardButton(SEND_YOUR_CONTACT_INFO, true),
                createKeyboardButton(CommandDescriptions.TO_MAIN_MENU_DESC)
        );
    }
}
