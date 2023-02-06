package pro.sky.telegrambotshelter.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.repository.UserRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pro.sky.telegrambotshelter.service.testConstants.UserServiceTestConstants.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TelegramCommandBot bot;
    @InjectMocks
    private UserService out;

    @ParameterizedTest
    @MethodSource("provideParamsForUpdateVolunteer")
    public void updateVolunteerList(LinkedHashSet<User> registeredVolunteers, Set<User> volunteersInDB, LinkedHashSet<User> expected) {
        when(userRepository.findVolunteers()).thenReturn(volunteersInDB);
        ReflectionTestUtils.setField(out, "volunteerList", registeredVolunteers, LinkedHashSet.class);
        out.updateVolunteerList();
        assertThat((LinkedHashSet<User>) ReflectionTestUtils.getField(out, "volunteerList")).containsExactlyElementsOf(expected);
    }

    @Test
    public void getNextVolunteer() {
        LinkedHashSet<User> given = new LinkedHashSet<>(List.of(USER_VOL_A, USER_VOL_B, USER_VOL_C));
        LinkedHashSet<User> expectedSet = new LinkedHashSet<>(List.of(USER_VOL_B, USER_VOL_C, USER_VOL_A));
        Long expectedChatId = USER_VOL_A.getChatId();

        ReflectionTestUtils.setField(out, "volunteerList", given, LinkedHashSet.class);

        assertThat(out.getNextVolunteer()).isEqualTo(expectedChatId);
        assertThat((LinkedHashSet<User>) ReflectionTestUtils.getField(out, "volunteerList")).containsExactlyElementsOf(expectedSet);
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