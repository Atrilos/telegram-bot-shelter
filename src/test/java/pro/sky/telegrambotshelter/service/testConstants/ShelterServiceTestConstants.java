package pro.sky.telegrambotshelter.service.testConstants;

import pro.sky.telegrambotshelter.model.Shelter;
import pro.sky.telegrambotshelter.model.User;
import pro.sky.telegrambotshelter.model.enums.ShelterType;

public class ShelterServiceTestConstants {
    public static final User USER_CAT_SHELTER = User.builder().currentShelter(ShelterType.CAT).build();
    public static final User USER_DOG_SHELTER = User.builder().currentShelter(ShelterType.DOG).build();
    public static final Shelter TEST_DOG_SHELTER = Shelter.builder().id(1L).isCatShelter(false).build();
    public static final Shelter TEST_CAT_SHELTER = Shelter.builder().id(2L).isCatShelter(true).build();
}
