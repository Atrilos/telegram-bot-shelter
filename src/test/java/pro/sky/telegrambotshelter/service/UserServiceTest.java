package pro.sky.telegrambotshelter.service;

import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendContact;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambotshelter.exception.AdoptionDayNullException;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @Spy
    private LinkedHashSet<User> volunteerList;
    @InjectMocks
    private UserService out;

    @ParameterizedTest
    @MethodSource("provideParamsForUpdateVolunteer")
    public void updateVolunteerList(LinkedHashSet<User> registeredVolunteers, Set<User> volunteersInDB, LinkedHashSet<User> expected) throws Exception {
        when(userRepository.findVolunteers()).thenReturn(volunteersInDB);
        volunteerList = registeredVolunteers;
        MockitoAnnotations.openMocks(this).close();

        out.updateVolunteerList();

        assertThat(volunteerList).containsExactlyElementsOf(expected);
    }

    @Test
    public void getNextVolunteer() throws Exception {
        LinkedHashSet<User> given = new LinkedHashSet<>(List.of(USER_VOL_A, USER_VOL_B, USER_VOL_C));
        LinkedHashSet<User> expectedSet = new LinkedHashSet<>(List.of(USER_VOL_B, USER_VOL_C, USER_VOL_A));
        Long expectedChatId = USER_VOL_A.getChatId();
        volunteerList = given;
        MockitoAnnotations.openMocks(this).close();

        assertThat(out.getNextVolunteer()).isEqualTo(expectedChatId);

        assertThat(volunteerList).containsExactlyElementsOf(expectedSet);
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
                            "1 SendContact invocations");

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
                            "2 SendMessage invocations");
            Condition<AbstractSendRequest<?>> conditionContact =
                    new Condition<>(m -> m.getClass().equals(SendContact.class)
                                         && m.getParameters().get("chat_id").equals(USER_VOL_A.getChatId()) && m.getParameters().get("first_name").equals(USER_DOG_ADOPTER_TRIAL_END.getFirstName())
                                         && m.getParameters().get("phone_number").equals(USER_DOG_ADOPTER_TRIAL_END.getPhoneNumber()),
                            "1 SendContact invocations");

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

    public static Stream<Arguments> provideParamsForUpdateVolunteer() {
        return Stream.of(
                Arguments.of(
                        new LinkedHashSet<>(List.of(USER_VOL_A, USER_VOL_B, USER_VOL_C)),
                        Set.of(USER_VOL_D),
                        new LinkedHashSet<>(List.of(USER_VOL_D))
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(USER_VOL_A, USER_VOL_B, USER_VOL_C)),
                        Set.of(USER_VOL_A, USER_VOL_B, USER_VOL_C),
                        new LinkedHashSet<>(List.of(USER_VOL_A, USER_VOL_B, USER_VOL_C))
                ),
                Arguments.of(
                        new LinkedHashSet<>(List.of(USER_VOL_A, USER_VOL_B)),
                        Set.of(USER_VOL_B, USER_VOL_C),
                        new LinkedHashSet<>(List.of(USER_VOL_B, USER_VOL_C))
                )
        );
    }
}