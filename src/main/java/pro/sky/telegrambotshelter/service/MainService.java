package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages;
import pro.sky.telegrambotshelter.model.enums.BotCommands;
import pro.sky.telegrambotshelter.model.enums.BotMenus;

import static pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages.UNSUPPORTED_RESPONSE_MSG;

@Service
@RequiredArgsConstructor
public class MainService {
    private final TelegramBot telegramBot;
    public void processTextMessage(Update update) {
        System.out.println("MainService processTextMessage");
        Long chatId = update.message().chat().id();
        BotCommands receivedCommand = BotCommands.fromString(update.message().text());
        if (receivedCommand == null) {
            telegramBot.execute(new SendMessage(chatId, UNSUPPORTED_RESPONSE_MSG));
            return;
        }
        receivedCommand.getCommandHandler().process(telegramBot, update);
        telegramBot.execute(new SendMessage(chatId, CommandResponseMessages.createListOfAvailableCommands()));
        System.out.println("   BotMenus.currentMenu  " +    BotMenus.currentMenu);
    }

}
