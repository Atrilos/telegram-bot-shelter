package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.*;
import pro.sky.telegrambotshelter.service.UserService;
import pro.sky.telegrambotshelter.utils.KeyboardUtils;
import java.util.*;

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

    public boolean adopterSentReportToday(User user){
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.YEAR) * 1000 + calendar.get(Calendar.DAY_OF_YEAR);
        return user.getLastReportDay() == today;
    }

    public boolean adopterSentPhotoToday(User user){
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.YEAR) * 1000 + calendar.get(Calendar.DAY_OF_YEAR);
        return user.getLastPhotoReportDay() == today;
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
        }
        else
            message = new SendMessage(chatId, REPORT_NOT_NEEDED);

        if (needReport) {
            ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard();
            message.replyMarkup(replyKeyboardMarkup);
            userService.changeCurrentMenu(user, CurrentMenu.REPORT);
        }
        bot.execute(message);
    }
}
