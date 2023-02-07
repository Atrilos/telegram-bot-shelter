package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.*;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambotshelter.exception.AdoptionDayNullException;
import pro.sky.telegrambotshelter.exception.UserNotFoundException;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pro.sky.telegrambotshelter.configuration.UIstrings.UIstrings.*;
import static pro.sky.telegrambotshelter.service.testConstants.UserServiceTestConstants.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TelegramCommandBot bot;
    @InjectMocks
    private UserService out;

    @Test
    public void getNextVolunteerPositive() {
        when(userRepository.findRandomVolunteer()).thenReturn(Optional.of(USER_VOL_A));

        assertThat(out.getNextVolunteer()).isEqualTo(USER_VOL_A.getChatId());
    }

    @Test
    public void getNextVolunteer_ShouldThrow() {
        when(userRepository.findRandomVolunteer()).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.getNextVolunteer()).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    public void shouldReturnOk_IfBothReportsSent() {
        try (MockedStatic<LocalDateTime> mockedStatic = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            UserService outSpy = getOutSpyForReportsTest(mockedStatic, USER_DOG_ADOPTER_REPORT_SENT);

            outSpy.checkAdoptersReports();

            verify(bot, never()).execute(any(SendMessage.class));
        }
    }

    private UserService getOutSpyForReportsTest(MockedStatic<LocalDateTime> mockedStatic, User user) {
        UserService outSpy = spy(out);
        LocalDateTime epoch40 = LocalDateTime.of(LocalDate.ofEpochDay(40L), LocalTime.MIN);

        when(bot.isTestMode()).thenReturn(false);
        doReturn(USER_VOL_A.getChatId()).when(outSpy).getNextVolunteer();
        mockedStatic.when(LocalDateTime::now).thenReturn(epoch40);
        when(userRepository.findByIsDogAdopterTrialTrueOrIsCatAdopterTrialTrue())
                .thenReturn(List.of(user));
        return outSpy;
    }

    @Test
    public void shouldAskForReport_IfBothReportsAbsent() {
        try (MockedStatic<LocalDateTime> mockedStatic = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            UserService outSpy = getOutSpyForReportsTest(mockedStatic, USER_DOG_ADOPTER_REPORT_NOT_SENT);
            ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
            Condition<AbstractSendRequest<?>> conditionMessage =
                    new Condition<>(m -> m.getClass().equals(SendMessage.class)
                                         && (m.getParameters().get("text").equals(SHOULD_SEND_REPORT) && m.getParameters().get("chat_id").equals(USER_DOG_ADOPTER_REPORT_NOT_SENT.getChatId())
                                             || (m.getParameters().get("text").equals(MISSING_REPORT) && m.getParameters().get("chat_id").equals(USER_VOL_A.getChatId()))),
                            "2 SendMessage invocations");
            Condition<AbstractSendRequest<?>> conditionContact =
                    new Condition<>(m -> m.getClass().equals(SendContact.class)
                                         && m.getParameters().get("chat_id").equals(USER_VOL_A.getChatId()) && m.getParameters().get("first_name").equals(USER_DOG_ADOPTER_REPORT_NOT_SENT.getFirstName())
                                         && m.getParameters().get("phone_number").equals(USER_DOG_ADOPTER_REPORT_NOT_SENT.getPhoneNumber()),
                            "1 SendContact invocation");

            outSpy.checkAdoptersReports();

            verify(bot, times(3)).execute(argumentCaptor.capture());
            assertThat(argumentCaptor.getAllValues()).areExactly(2, conditionMessage);
            assertThat(argumentCaptor.getAllValues()).areExactly(1, conditionContact);
        }
    }

    @Test
    public void shouldConfirmTrialEnd() {
        try (MockedStatic<LocalDateTime> mockedStatic = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            UserService outSpy = getOutSpyForReportsTest(mockedStatic, USER_DOG_ADOPTER_TRIAL_END);
            ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
            Condition<AbstractSendRequest<?>> conditionMessage =
                    new Condition<>(m -> m.getClass().equals(SendMessage.class)
                                         && m.getParameters().get("text").equals(TRIAL_PERIOD_OVER) && m.getParameters().get("chat_id").equals(USER_VOL_A.getChatId()),
                            "1 SendMessage invocation");
            Condition<AbstractSendRequest<?>> conditionContact =
                    new Condition<>(m -> m.getClass().equals(SendContact.class)
                                         && m.getParameters().get("chat_id").equals(USER_VOL_A.getChatId()) && m.getParameters().get("first_name").equals(USER_DOG_ADOPTER_TRIAL_END.getFirstName())
                                         && m.getParameters().get("phone_number").equals(USER_DOG_ADOPTER_TRIAL_END.getPhoneNumber()),
                            "1 SendContact invocation");

            outSpy.checkAdoptersReports();

            verify(bot, times(2)).execute(argumentCaptor.capture());
            assertThat(argumentCaptor.getAllValues()).areExactly(1, conditionMessage);
            assertThat(argumentCaptor.getAllValues()).areExactly(1, conditionContact);
        }
    }

    @Test
    public void shouldThrowIfAdoptionDayNull_WithAdopterFlag() {
        try (MockedStatic<LocalDateTime> mockedStatic = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            UserService outSpy = getOutSpyForReportsTest(mockedStatic, USER_DOG_ADOPTER_NO_ADOPTION_DAY);

            assertThatThrownBy(outSpy::checkAdoptersReports).isInstanceOf(AdoptionDayNullException.class);
        }
    }

    @Test
    public void registerIfAbsentPositive() {
        Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id:111, from:{id:100, first_name:A}, chat:{id:100}}}");
        when(userRepository.findByChatId(anyLong())).thenReturn(Optional.empty());

        assertThatNoException().isThrownBy(() -> out.registerIfAbsent(update));

        verify(userRepository).findByChatId(anyLong());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void registerIfAbsent_AlreadyRegistered() {
        Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id:111, from:{id:100, first_name:A}, chat:{id:100}}}");
        when(userRepository.findByChatId(anyLong())).thenReturn(Optional.of(USER_VOL_B));

        assertThatNoException().isThrownBy(() -> out.registerIfAbsent(update));

        verify(userRepository).findByChatId(anyLong());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void registerContactPositive() {
        Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id:%d, contact:{user_id: %s, first_name: %s, phone_number: %s}}}"
                .formatted(USER_VOL_C.getChatId(), USER_VOL_C.getId(), USER_VOL_C.getFirstName(), 1001));
        Contact contact = update.message().contact();
        User testUser = USER_VOL_C.withPhoneNumber(null);

        when(userRepository.findByChatId(any())).thenReturn(Optional.of(testUser));

        assertThatNoException().isThrownBy(() -> out.registerContact(contact));
        verify(userRepository).saveAndFlush(testUser.withPhoneNumber("1001"));
    }

    @Test
    public void registerContactNegative() {
        Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id:%d, contact:{user_id: %s, first_name: %s, phone_number: %s}}}"
                .formatted(USER_VOL_C.getChatId(), USER_VOL_C.getId(), USER_VOL_C.getFirstName(), 1001));
        Contact contact = update.message().contact();

        when(userRepository.findByChatId(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.registerContact(contact)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    public void getUserNegative() {
        when(userRepository.findByChatId(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> out.getUser(1L)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void processReport_Photo() {
        Update update = BotUtils.parseUpdate(("{update_id:1, message: {message_id:%d, from:{id:%d, first_name:%s}, chat:{id:100}, " +
                                              "photo: [{file_id: 1,file_unique_id:1, width:100,height:100,file_size:100}," +
                                              "{file_id: 2,file_unique_id:2, width:200,height:200,file_size:200}]}}")
                .formatted(USER_VOL_C.getChatId(), USER_VOL_C.getId(), USER_VOL_C.getFirstName()));
        ArgumentCaptor<BaseRequest<?,?>> captorBaseRequest = ArgumentCaptor.forClass(BaseRequest.class);
        ArgumentCaptor<User> captorUser = ArgumentCaptor.forClass(User.class);
        Condition<BaseRequest<?,?>> conditionMessage =
                new Condition<>(m -> m.getClass().equals(SendMessage.class), "1 SendMessage invocation");
        Condition<BaseRequest<?,?>> conditionContact =
                new Condition<>(m -> m.getClass().equals(SendContact.class), "1 SendContact invocation");
        Condition<BaseRequest<?,?>> conditionPhoto =
                new Condition<>(m -> m.getClass().equals(SendPhoto.class), "1 SendPhoto invocation");
        User testUser = USER_VOL_C.withLastPhotoReportDay(null);

        when(userRepository.findRandomVolunteer()).thenReturn(Optional.of(USER_VOL_A));

        out.processReport(update, testUser);

        verify(bot, times(3)).execute(captorBaseRequest.capture());
        verify(userRepository).saveAndFlush(captorUser.capture());
        assertThat(captorBaseRequest.getAllValues()).areExactly(1, conditionMessage);
        assertThat(captorBaseRequest.getAllValues()).areExactly(1, conditionContact);
        assertThat(captorBaseRequest.getAllValues()).areExactly(1, conditionPhoto);
        assertThat(captorUser.getAllValues()).hasSize(1);
        assertThat(captorUser.getAllValues().get(0).getLastPhotoReportDay()).isNotNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void processReport_Text() {
        Update update = BotUtils.parseUpdate(("{update_id:1, message: {message_id:%d, from:{id:%d, first_name:%s}, chat:{id:100}, text: test}}")
                .formatted(USER_VOL_C.getChatId(), USER_VOL_C.getId(), USER_VOL_C.getFirstName()));
        ArgumentCaptor<BaseRequest<?,?>> captorBaseRequest = ArgumentCaptor.forClass(BaseRequest.class);
        ArgumentCaptor<User> captorUser = ArgumentCaptor.forClass(User.class);
        Condition<BaseRequest<?,?>> conditionMessage =
                new Condition<>(m -> m.getClass().equals(SendMessage.class), "1 SendMessage invocation");
        Condition<BaseRequest<?,?>> conditionContact =
                new Condition<>(m -> m.getClass().equals(SendContact.class), "1 SendContact invocation");
        Condition<BaseRequest<?,?>> conditionForward =
                new Condition<>(m -> m.getClass().equals(ForwardMessage.class), "1 ForwardMessage invocation");
        User testUser = USER_VOL_C.withLastReportDay(null);

        when(userRepository.findRandomVolunteer()).thenReturn(Optional.of(USER_VOL_A));

        out.processReport(update, testUser);

        verify(bot, times(3)).execute(captorBaseRequest.capture());
        verify(userRepository).saveAndFlush(captorUser.capture());
        assertThat(captorBaseRequest.getAllValues()).areExactly(1, conditionMessage);
        assertThat(captorBaseRequest.getAllValues()).areExactly(1, conditionContact);
        assertThat(captorBaseRequest.getAllValues()).areExactly(1, conditionForward);
        assertThat(captorUser.getAllValues()).hasSize(1);
        assertThat(captorUser.getAllValues().get(0).getLastReportDay()).isNotNull();
    }
}