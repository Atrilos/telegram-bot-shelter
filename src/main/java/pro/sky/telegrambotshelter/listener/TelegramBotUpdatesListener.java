package pro.sky.telegrambotshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.service.MainService;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final MainService mainService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            try {
                log.info("Processing update: {}", update);
                if (update.message() != null) {
                    User user = mainService.authenticateUser(update);
                    mainService.processMessageByType(update, user);
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
