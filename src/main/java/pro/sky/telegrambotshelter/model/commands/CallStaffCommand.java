package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendContact;
import com.pengrad.telegrambot.request.SendMessage;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.service.UserService;
import pro.sky.telegrambotshelter.utils.KeyboardUtils;

public class CallStaffCommand extends ExecutableBotCommand {


    public CallStaffCommand(String command, String description) {
        super(command, description);
    }

    private ReplyKeyboardMarkup createReplyKeyboard() {

        KeyboardButton[] acceptButton =
                KeyboardUtils.createKeyboardButton("Передать ваш контакт волонтеру", true);
        KeyboardButton[] cancelButton =
                KeyboardUtils.createKeyboardButton("/to_main_menu", false);

        return KeyboardUtils.createKeyboard(acceptButton, cancelButton);
    }

    @Override
    public void execute(TelegramBot bot, Update update, User user, UserService userService) {
        Long chatId = update.message().chat().id();

        if (user.getPhoneNumber() == null) {
            SendMessage message = new SendMessage(chatId, "Разрешаете ли вы передать ваш контакт волонтеру?");
            ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard();
            message.replyMarkup(replyKeyboardMarkup);
            bot.execute(message);
        } else {
            Long availableVolunteerId = findAvailableVolunteer(userService);
            sendMessageToVolunteer(bot, availableVolunteerId, user);
        }

        userService.changeCurrentMenu(user, CurrentMenu.MAIN);
    }

    private Long findAvailableVolunteer(UserService userService) {
        return userService.getNextVolunteer();
    }

    private void sendMessageToVolunteer(TelegramBot bot, Long receiverId, User user) {
        bot.execute(new SendMessage(receiverId, "Пожалуйста свяжитесь с пользователем"));
        bot.execute(new SendContact(receiverId, user.getPhoneNumber(), user.getFirstName()));
    }
}
