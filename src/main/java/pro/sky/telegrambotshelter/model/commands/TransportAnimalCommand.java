package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.service.UserService;

public class TransportAnimalCommand extends ExecutableBotCommand {

    public TransportAnimalCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(TelegramBot bot, Update update, User user, UserService userService) {

    }
}
