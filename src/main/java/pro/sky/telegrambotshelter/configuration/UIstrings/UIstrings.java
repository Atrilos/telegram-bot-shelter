package pro.sky.telegrambotshelter.configuration.UIstrings;

import pro.sky.telegrambotshelter.model.enums.AvailableCommands;

import java.util.Arrays;

/**
 * Класс, содержащий константы для текстовых ответов на запросы боту.
 */
public class UIstrings {
    public static final String START_RESPONSE = """
            ПРИВЕТСТВУЕМ, %s!
            """;
    public static final String UNSUPPORTED_COMMAND = "Команда не поддерживается";
    public static final String SEND_YOUR_CONTACT_INFO = "Передать ваш контакт волонтеру";
    public static final String CONFIRM_SENDING_CONTACT_INFO = "Разрешаете ли вы передать ваш контакт волонтеру?";
    public static final String PLEASE_CONTACT_USER = "Пожалуйста свяжитесь с пользователем";
    public static final String SELECT_SHELTER = "Какой приют Вас интересует?";
    public static final String DOG_SHELTER_SELECTED = "Выбран приют для собак";
    public static final String CAT_SHELTER_SELECTED = "Выбран приют для кошек";
    public static final String REPORT_BOTH = "Отправьте отчет и фото питомца";
    public static final String REPORT_PHOTO = "Отправьте фото питомца";
    public static final String REPORT_TEXT = "Отправьте отчет о питомце";
    public static final String REPORT_SENT = "Отчет за сегодня отправлен";
    public static final String REPORT_NOT_NEEDED = "Вам не нужно отправлять отчет о питомце";
    public static final String CAT_SHELTER = "Приют для кошек";
    public static final String DOG_SHELTER = "Приют для собак";
    public static final String WHAT_DO_YOU_WANT_TO_KNOW = "Что Вы хотите узнать о приюте?";
    public static final String USER_PHOTO_REPORT = "Фото отчет от пользователя";
    public static final String USER_REPORT = "Отчет от пользователя";
    public static final String SHOULD_SEND_REPORT = "Вам следует отправить отчет о питомце";
    public static final String MISSING_REPORT = "Вчерашний отчет о питомце не был отправлен";
    public static final String TRIAL_PERIOD_OVER = "Испытательный период закончился";
    public static final String CONTACT_ALREADY_IN_DB = "Ваши контактные данные уже внесены в базу данных";
    public static final String CONTACT_SUCCESSFULLY_ADDED = "Данные успешно добавлены";
    public static final String OUR_LOCATION = "[Наше местоположение](%s)";
    public static final String HELP_RESPONSE = createListOfAvailableCommands();


    /**
     * Вспомогательный метод для формирования справки
     *
     * @return текст для справки /help
     */
    private static String createListOfAvailableCommands() {
        StringBuilder sb = new StringBuilder("Список доступных команд:\n");
        Arrays.stream(AvailableCommands.values()).forEach(command -> sb.append(command.getCommand())
                .append(" - ")
                .append(command.getDescription())
                .append("\n"));
        return sb.toString();
    }
}
