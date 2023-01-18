package pro.sky.telegrambotshelter.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeDefault;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;

import java.util.Arrays;

@Configuration
public class TelegramBotConfiguration {

    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        BotCommand[] availableBotCommands =
                Arrays
                        .stream(AvailableCommands.values())
                        .map(AvailableCommands::getCommand)
                        .toArray(BotCommand[]::new);
        bot.execute(new SetMyCommands(availableBotCommands).scope(new BotCommandScopeDefault()));
        return bot;
    }

}
