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

import java.util.Calendar;
import java.util.EnumSet;

@Component
public class ReportCommand extends ExecutableBotCommand {

    private final UserService userService;
    private final TelegramCommandBot bot;
    private final String reportBoth = "Отправьте отчет и фото питомца";
    private final String reportPhoto = "Отправьте фото питомца";
    private final String reportText = "Отправьте отчет о питомце";
    private final String reportSent = "Сегодшяшный отчет отправлен";
    private final String notAdopter = "Вы не забирали животных из нашего приюта";

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
        boolean needReport = user.getIsAdopter() && !user.isAdoptionApproved();
        if (needReport) {
            if (adopterSentReportToday(user) && adopterSentPhotoToday(user))
                message = new SendMessage(chatId, reportSent);
            else if (adopterSentPhotoToday(user))
                message = new SendMessage(chatId, reportText);
            else if (adopterSentReportToday(user))
                message = new SendMessage(chatId, reportPhoto);
            else
                message = new SendMessage(chatId, reportBoth);
        }
        else
            message = new SendMessage(chatId, notAdopter);

        if (needReport) {
            ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard();
            message.replyMarkup(replyKeyboardMarkup);
            userService.changeCurrentMenu(user, CurrentMenu.REPORT);
        }
        bot.execute(message);
    }
}
