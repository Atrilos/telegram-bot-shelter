package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.model.commands.CallStaffCommand;
import pro.sky.telegrambotshelter.model.commands.ExecutableBotCommand;
import pro.sky.telegrambotshelter.model.commands.StartCommand;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pro.sky.telegrambotshelter.service.testConstants.UserServiceTestConstants.USER_VOL_C;

@ExtendWith(MockitoExtension.class)
class MainServiceTest {

    @Mock
    private TelegramCommandBot bot;
    @Mock
    private UserService userService;
    @Spy
    private ArrayList<ExecutableBotCommand> botCommands;
    @Mock
    private CallStaffCommand callStaffCommand;
    @Mock
    private StartCommand startCommand;
    @InjectMocks
    private MainService out;

    @Test
    public void authenticateUser() {
        Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id:101, from:{id:100}, chat:{id:100}}}");

        out.authenticateUser(update);

        verify(userService).registerIfAbsent(any());
        verify(userService).getUser(anyLong());
    }

    @Test
    public void processContact_WhenUserInCallStaffMenu() {
        prepareBotCommands(callStaffCommand, startCommand);
        Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id:%d, contact:{user_id: %s, first_name: %s, phone_number: %s}}}"
                .formatted(USER_VOL_C.getChatId(), USER_VOL_C.getId(), USER_VOL_C.getFirstName(), USER_VOL_C.getPhoneNumber()));
        Contact contact = update.message().contact();

        when(userService.registerContact(contact)).thenReturn(USER_VOL_C);

        out.processMessageByType(update, USER_VOL_C);

        verify(callStaffCommand).execute(update, USER_VOL_C);
    }

    @Test
    public void processContact_WhenUserInMainMenu() {
        prepareBotCommands(callStaffCommand, startCommand);
        User userInMainMenu = USER_VOL_C.toBuilder().currentMenu(CurrentMenu.MAIN).build();
        Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id:%d, contact:{user_id: %s, first_name: %s, phone_number: %s}}}"
                .formatted(userInMainMenu.getChatId(), userInMainMenu.getId(), userInMainMenu.getFirstName(), userInMainMenu.getPhoneNumber()));
        Contact contact = update.message().contact();

        when(userService.registerContact(contact)).thenReturn(userInMainMenu);

        out.processMessageByType(update, userInMainMenu);

        verify(startCommand).execute(update, userInMainMenu);
    }

    private void prepareBotCommands(ExecutableBotCommand... commands) {
        botCommands.addAll(List.of(commands));
    }

    @Test
    public void processTextMessage_CommandRecognized() {
        prepareBotCommands(callStaffCommand, startCommand);
        Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id:101, text:\"/start\", from:{id:%d}, chat:{id:%d}}}"
                .formatted(USER_VOL_C.getChatId(), USER_VOL_C.getChatId()));

        when(startCommand.isSupported(eq("/start"), any(CurrentMenu.class))).thenReturn(true);

        out.processMessageByType(update, USER_VOL_C);

        verify(startCommand).execute(any(), any());
    }

    @Test
    public void processTextMessage_UnknownCommand() {
        prepareBotCommands(callStaffCommand, startCommand);
        Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id:101, text:123, from:{id:%d}, chat:{id:%d}}}"
                .formatted(USER_VOL_C.getChatId(), USER_VOL_C.getChatId()));

        out.processMessageByType(update, USER_VOL_C);

        verify(bot).execute(any(SendMessage.class));
    }
}