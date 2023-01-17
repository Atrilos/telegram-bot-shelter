package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.telegrambotshelter.model.enums.AvailableCommands;

import static pro.sky.telegrambotshelter.configuration.messages.CommandResponseMessages.UNSUPPORTED_RESPONSE_MSG;

@Service
@RequiredArgsConstructor
public class MainService {
    private final TelegramBot telegramBot;

    public void processTextMessage(Update update) {
        Long chatId = update.message().chat().id();
        AvailableCommands receivedCommand = AvailableCommands.fromString(update.message().text());
        if (receivedCommand == null) {
            telegramBot.execute(new SendMessage(chatId, UNSUPPORTED_RESPONSE_MSG));
            return;
        }
        receivedCommand.getCommandHandler().process(telegramBot, update);
    }
}
