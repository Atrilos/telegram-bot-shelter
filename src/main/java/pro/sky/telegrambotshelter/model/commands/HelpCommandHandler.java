package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages;

public class HelpCommandHandler implements CommandHandler {
    @Override
    public void process(TelegramBot bot, Update update) {
        Long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, CommandResponseMessages.HELP_RESPONSE_MSG));
    }
}
