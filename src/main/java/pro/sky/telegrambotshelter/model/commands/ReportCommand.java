package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;
import pro.sky.telegrambotshelter.utils.KeyboardUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.EnumSet;

import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.*;

@Component
public class ReportCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;

    public ReportCommand(UserService userService, TelegramCommandBot bot) {
        super(AvailableCommands.REPORT.getCommand(),
                AvailableCommands.REPORT.getDescription(),
                AvailableCommands.REPORT.isTopLevel(),
                EnumSet.allOf(CurrentMenu.class)
        );
        this.userService = userService;
        this.bot = bot;
    }

    static ReplyKeyboardMarkup createReplyKeyboard() {
        KeyboardButton[] backButton =
                KeyboardUtils.createKeyboardButton(AvailableCommands.TO_MAIN_MENU.getDescription());

        return KeyboardUtils.createKeyboard(backButton);
    }

    public boolean adopterSentReportToday(User user) {
        LocalDateTime lastReportDay = user.getLastReportDay();
        return lastReportDay.get(ChronoField.EPOCH_DAY) == LocalDateTime.now().get(ChronoField.EPOCH_DAY);
    }

    public boolean adopterSentPhotoToday(User user) {
        LocalDateTime lastPhotoReportDay = user.getLastPhotoReportDay();
        return lastPhotoReportDay.get(ChronoField.EPOCH_DAY) == LocalDateTime.now().get(ChronoField.EPOCH_DAY);
    }

    @Override
    public void execute(Update update, User user) {
        Long chatId = update.message().chat().id();
        SendMessage message;
        boolean needReport = user.getIsCatAdopterTrial() || !user.getIsDogAdopterTrial();
        if (needReport) {
            if (adopterSentReportToday(user) && adopterSentPhotoToday(user))
                message = new SendMessage(chatId, REPORT_SENT);
            else if (adopterSentPhotoToday(user))
                message = new SendMessage(chatId, REPORT_TEXT);
            else if (adopterSentReportToday(user))
                message = new SendMessage(chatId, REPORT_PHOTO);
            else
                message = new SendMessage(chatId, REPORT_BOTH);
        } else
            message = new SendMessage(chatId, REPORT_NOT_NEEDED);

        if (needReport) {
            ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard();
            message.replyMarkup(replyKeyboardMarkup);
            userService.changeCurrentMenu(user, CurrentMenu.REPORT);
        }
        bot.execute(message);
    }
}
