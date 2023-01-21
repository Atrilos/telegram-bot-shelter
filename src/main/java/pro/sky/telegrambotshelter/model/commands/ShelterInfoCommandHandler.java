package pro.sky.telegrambotshelter.model.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import pro.sky.telegrambotshelter.model.enums.BotMenus;

public class ShelterInfoCommandHandler implements CommandHandler {
    @Override
    public void process(TelegramBot bot, Update update) {
        BotMenus.currentMenu = BotMenus.SHELTER_INFO;
    }
}
