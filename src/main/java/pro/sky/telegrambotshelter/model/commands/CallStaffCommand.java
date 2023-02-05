package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendContact;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.configuration.UIstrings.CommandDescriptions;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;
import pro.sky.telegrambotshelter.utils.KeyboardUtils;

import java.util.EnumSet;

import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.*;

@Component
public class CallStaffCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public CallStaffCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.CALL_STAFF.getCommand(),
                AvailableCommands.CALL_STAFF.getDescription(),
                AvailableCommands.CALL_STAFF.isTopLevel(),
                EnumSet.allOf(CurrentMenu.class)
        );
        this.userService = userService;
        this.bot = bot;
    }

    private ReplyKeyboardMarkup createReplyKeyboard() {

        KeyboardButton[] acceptButton =
                KeyboardUtils.createKeyboardButton(SEND_YOUR_CONTACT_INFO, true);
        KeyboardButton[] cancelButton =
                KeyboardUtils.createKeyboardButton(CommandDescriptions.TO_MAIN_MENU_DESC);

        return KeyboardUtils.createKeyboard(acceptButton, cancelButton);
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();

        if (user.getPhoneNumber() == null) {
            SendMessage message = new SendMessage(chatId, CONFIRM_SENDING_CONTACT_INFO);
            ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard();
            message.replyMarkup(replyKeyboardMarkup);
            bot.execute(message);
            userService.changeCurrentMenu(user, CurrentMenu.CALL_STAFF);
        } else {
            Long availableVolunteerId = userService.getNextVolunteer();
            sendMessageToVolunteer(bot, availableVolunteerId, user);
            userService.changeCurrentMenu(user, CurrentMenu.MAIN);
        }
    }

    private void sendMessageToVolunteer(TelegramCommandBot bot, Long receiverId, User user) {
        bot.execute(new SendMessage(receiverId, PLEASE_CONTACT_USER));
        bot.execute(new SendContact(receiverId, user.getPhoneNumber(), user.getFirstName()));
    }
}
