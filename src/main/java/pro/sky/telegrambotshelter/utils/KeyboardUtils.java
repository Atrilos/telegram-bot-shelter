package pro.sky.telegrambotshelter.utils;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.jetbrains.annotations.NotNull;

public class KeyboardUtils {

    @NotNull
    public static ReplyKeyboardMarkup createKeyboard(KeyboardButton[]... keyboardButtons) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardButtons);
        replyKeyboardMarkup.oneTimeKeyboard(true);
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static KeyboardButton[] createKeyboardButton(String text, boolean requestContact) {
        return new KeyboardButton[]{new KeyboardButton(text).requestContact(requestContact)};
    }
}
