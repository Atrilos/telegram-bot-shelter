package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendContact;
import com.pengrad.telegrambot.request.SendMessage;
import org.jetbrains.annotations.NotNull;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.enums.UserState;
import pro.sky.telegrambotshelter.service.UserService;

public class CallStaffCommand extends ExecutableBotCommand {


    public CallStaffCommand(String command, String description) {
        super(command, description);
    }

    @NotNull
    private static ReplyKeyboardMarkup createKeyboard() {
        KeyboardButton[] acceptButton = new KeyboardButton[]{
                new KeyboardButton("Передать ваш контакт волонтеру").requestContact(true)
        };
        KeyboardButton[] cancelButton = new KeyboardButton[]{new KeyboardButton("Назад")};
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(acceptButton, cancelButton);
        replyKeyboardMarkup.oneTimeKeyboard(true);
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    @Override
    public void execute(TelegramBot bot, Update update, UserService userService) {
        Long chatId = update.message().chat().id();
        User user = userService.getUser(chatId);

        if (user.getPhoneNumber() == null) {
            SendMessage message = new SendMessage(chatId, "Разрешаете ли вы передать ваш контакт волонтеру?");
            ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboard();
            message.replyMarkup(replyKeyboardMarkup);
            bot.execute(message);
            userService.changeUserState(user, UserState.CALL_STAFF_STATE);
        } else {
            Long availableVolunteerId = findAvailableVolunteer(userService);
            sendMessageToVolunteer(bot, availableVolunteerId, user);
            userService.changeUserState(user, UserState.BASIC_STATE);
        }
    }

    private Long findAvailableVolunteer(UserService userService) {
        return userService.getNextVolunteer();
    }

    private void sendMessageToVolunteer(TelegramBot bot, Long receiverId, User user) {
        bot.execute(new SendMessage(receiverId, "Пожалуйста свяжитесь с пользователем"));
        bot.execute(new SendContact(receiverId, user.getPhoneNumber(), user.getFirstName()));
    }
}
