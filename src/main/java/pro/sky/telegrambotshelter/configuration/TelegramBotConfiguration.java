package pro.sky.telegrambotshelter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;

import java.util.Arrays;
import java.util.List;

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

    /**
     * Создание бина телеграм бота
     * @return созданный {@link TelegramCommandBot}
     */
    @Bean
    public TelegramCommandBot telegramCommandBot() {
        TelegramCommandBot bot = new TelegramCommandBot(token);
        List<AvailableCommands> topLevelCommands =
                Arrays
                        .stream(AvailableCommands.values())
                        .filter(AvailableCommands::isTopLevel)
                        .toList();
        topLevelCommands
                .forEach(bot::registerCommand);
        bot.createMainMenu();
        return bot;
    }

}
