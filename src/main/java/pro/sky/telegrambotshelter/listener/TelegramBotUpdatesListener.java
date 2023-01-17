package pro.sky.telegrambotshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final String welcomeText = "ПРИВЕТСТВУЕМ \n" +
            "/adopt  Взять собаку из приюта \n" +
            "/shelterInfo  Узнать информацию о приюте \n" +
            "/report  Прислать отчет о питомце \n" +
            "/callStaff  Позвать волонтера.";

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        if (updates == null || updates.size() == 0)
            return CONFIRMED_UPDATES_NONE;
//        updates.forEach(update -> {
//            log.info("Processing update: {}", update);
//        });
        Update lastUpdate = updates.get(updates.size() - 1);
        return  processLastUpdate(lastUpdate);
    }

    public int processLastUpdate(Update update) {
        long chatID = update.message().chat().id();
        String text = update.message().text();
        log.info("Processing update: " + update);
        if (text.equals("/start")){
            SendMessage message = new SendMessage(chatID, welcomeText);
            SendResponse response = telegramBot.execute(message);
        } else if (text.equals("/adopt")){

        } else if (text.equals("/shelterInfo")){

        } else if (text.equals("/report")){

        } else if (text.equals("/callStaff")){

        }
        return update.updateId();
    }
}
