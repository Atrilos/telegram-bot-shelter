package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import static pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages.START_RESPONSE_MSG;

public class StartCommandHandler implements CommandHandler {

    @Override
    public void process(TelegramBot bot, Update update) {
        Long chatId = update.message().chat().id();
        String responseMsg = START_RESPONSE_MSG.formatted(update.message().from().firstName());
        bot.execute(new SendMessage(chatId, responseMsg));
    }
}
