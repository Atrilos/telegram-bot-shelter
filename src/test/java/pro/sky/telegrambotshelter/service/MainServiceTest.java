package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MainServiceTest {

    @Mock
    private TelegramCommandBot bot;
    @Mock
    private UserService userService;
    @Spy
    private List<ExecutableBotCommand> botCommands;
    @InjectMocks
    private MainService out;

    @Test
    public void authenticateUser() {
        Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id:101, from:{id:100}, chat:{id:100}}}");

        out.authenticateUser(update);

        verify(userService).registerIfAbsent(any());
        verify(userService).getUser(anyLong());
    }
}