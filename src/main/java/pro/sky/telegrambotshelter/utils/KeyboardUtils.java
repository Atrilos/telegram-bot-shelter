package pro.sky.telegrambotshelter.utils;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.jetbrains.annotations.NotNull;

/**
 * Вспомогательный класс для создания {@link ReplyKeyboardMarkup}
 */
public class KeyboardUtils {

    /**
     * Метод для создания клавиатуры, в которой в каждом ряду одна кнопка
     * @param keyboardButtons массив массивов {@link KeyboardButton} для создания клавиатуры
     * @return клавиатура с заданными параметрами
     */
    @NotNull
    public static ReplyKeyboardMarkup createKeyboard(KeyboardButton[]... keyboardButtons) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardButtons);
        replyKeyboardMarkup.oneTimeKeyboard(true);
        replyKeyboardMarkup.resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    /**
     * Метод для создания кнопок клавиатуры по тексту и дополнителым параметрам
     * @param text текст кнопки
     * @param requestContact boolean-флаг запроса контакта
     * @return массив, содержащий кнопку с заданными параметрами
     */
    public static KeyboardButton[] createKeyboardButton(String text, boolean requestContact) {
        return new KeyboardButton[]{new KeyboardButton(text).requestContact(requestContact)};
    }
}
