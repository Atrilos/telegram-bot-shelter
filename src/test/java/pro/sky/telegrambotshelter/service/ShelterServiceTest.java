package pro.sky.telegrambotshelter.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambotshelter.exception.ShelterNotFoundException;
import pro.sky.telegrambotshelter.model.Shelter;
import pro.sky.telegrambotshelter.model.bot.TelegramCommandBot;
import pro.sky.telegrambotshelter.repository.ShelterRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static pro.sky.telegrambotshelter.service.testConstants.ShelterServiceTestConstants.*;

@ExtendWith(MockitoExtension.class)
class ShelterServiceTest {

    @Mock
    private TelegramCommandBot bot;
    @Mock
    private ShelterRepository shelterRepository;
    @InjectMocks
    private ShelterService out;

    @Test
    public void getShelterPositive() {
        List<Shelter> expectedCatShelter = List.of(TEST_CAT_SHELTER);
        List<Shelter> expectedDogShelter = List.of(TEST_DOG_SHELTER);

        when(shelterRepository.findByIsCatShelter(true)).thenReturn(expectedCatShelter);
        when(shelterRepository.findByIsCatShelter(false)).thenReturn(expectedDogShelter);

        assertThat(out.getShelter(USER_CAT_SHELTER)).isEqualTo(expectedCatShelter.get(0));
        assertThat(out.getShelter(USER_DOG_SHELTER)).isEqualTo(expectedDogShelter.get(0));

        assertThat(out.getShelter(true)).isEqualTo(expectedCatShelter.get(0));
        assertThat(out.getShelter(false)).isEqualTo(expectedDogShelter.get(0));
    }

    @Test
    public void getShelterNegative() {
        when(shelterRepository.findByIsCatShelter(true)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> out.getShelter(true)).isInstanceOf(ShelterNotFoundException.class);
    }

    @Test
    public void testGetShelter() {
    }
}