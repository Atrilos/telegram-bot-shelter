package pro.sky.telegrambotshelter.service.testConstants;

import pro.sky.telegrambotshelter.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserServiceTestConstants {
    // Значение id не важно, но добавлено на случай изменения имплементации
    public static final User USER_VOL_A = User.builder().id(1L).firstName("A").chatId(1L).isVolunteer(true).phoneNumber("123").build();
    public static final User USER_VOL_B = User.builder().id(2L).firstName("B").chatId(2L).isVolunteer(true).phoneNumber("124").build();
    public static final User USER_VOL_C = User.builder().id(3L).firstName("C").chatId(3L).isVolunteer(true).phoneNumber("125").build();
    public static final User USER_VOL_D = User.builder().id(4L).firstName("D").chatId(4L).isVolunteer(true).phoneNumber("126").build();
    public static final User USER_DOG_ADOPTER_REPORT_SENT = User.builder().id(5L).firstName("A").chatId(5L).isVolunteer(false).phoneNumber("127")
            .isDogAdopterTrial(true).adoptionDay(LocalDateTime.of(LocalDate.ofEpochDay(38), LocalTime.MIN))
            .lastReportDay(LocalDateTime.of(LocalDate.ofEpochDay(40), LocalTime.MIN))
            .lastPhotoReportDay(LocalDateTime.of(LocalDate.ofEpochDay(40), LocalTime.MIN)).build();
    public static final User USER_DOG_ADOPTER_REPORT_NOT_SENT = User.builder().id(6L).firstName("B").chatId(6L).isVolunteer(false).phoneNumber("128")
            .isDogAdopterTrial(true).adoptionDay(LocalDateTime.of(LocalDate.ofEpochDay(40), LocalTime.MIN)).build();
    public static final User USER_DOG_ADOPTER_TRIAL_END = User.builder().id(7L).firstName("C").chatId(7L).isVolunteer(false).phoneNumber("129")
            .isDogAdopterTrial(true).adoptionDay(LocalDateTime.of(LocalDate.ofEpochDay(10), LocalTime.MIN))
            .lastReportDay(LocalDateTime.of(LocalDate.ofEpochDay(40), LocalTime.MIN))
            .lastPhotoReportDay(LocalDateTime.of(LocalDate.ofEpochDay(40), LocalTime.MIN)).build();
    public static final User USER_DOG_ADOPTER_NO_ADOPTION_DAY = User.builder().id(8L).firstName("D").chatId(8L).isVolunteer(false).phoneNumber("130")
            .isDogAdopterTrial(true).build();
}
