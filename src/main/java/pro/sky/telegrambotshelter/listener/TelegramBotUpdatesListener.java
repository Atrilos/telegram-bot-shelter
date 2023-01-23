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

/**
 * Основная часть обработчика, получаемых сообщений в боте. Слушатель событий (event listener) получает обновления
 * бота, которые перенаправляет для дальнейшей обработки. Реализован на основе асинхронного Long Polling запроса.
 * Имитирует WebHook запросы.
 */
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

    /**
     * Слушатель событий. Получает доступные обновления в качестве листа, в конце отмечает их как обработанные.
     * @param updates доступные обновления
     * @return новый offset, то есть отмечает полученные сообщения как обработанные (сообщения с идентификатором
     * меньше offset считаются обработанными, больше или равные - новые, необработанные сообщения).
     */
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
