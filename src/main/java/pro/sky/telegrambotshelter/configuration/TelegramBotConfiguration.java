package pro.sky.telegrambotshelter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;

/**
 * Конфигурация телеграм-бота
 */
@Configuration
public class TelegramBotConfiguration {

    /**
     * Значение токена бота
     */
    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    public TelegramCommandBot telegramCommandBot() {
        TelegramCommandBot bot = new TelegramCommandBot(token);
        for (AvailableCommands commands : AvailableCommands.values()) {
            bot.registerCommand(commands.getCommand());
        }
        bot.createMainMenu();
        return bot;
    }

}
