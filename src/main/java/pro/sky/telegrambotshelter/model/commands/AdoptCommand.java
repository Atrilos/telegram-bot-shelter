package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import pro.sky.telegrambotshelter.service.UserService;

public class AdoptCommand extends ExecutableBotCommand {
    public AdoptCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(TelegramBot bot, Update update, UserService userService) {

    }
}
