package pro.sky.telegrambotshelter.service.testConstants;

import pro.sky.telegrambotshelter.model.User;

public class UserServiceTestConstants {
    // Значение id не важно, но добавлено на случай изменения имплементации
    public static final User USER_VOL_A = User.builder().id(1L).firstName("A").chatId(1L).isVolunteer(true).phoneNumber("123").build();
    public static final User USER_VOL_B = User.builder().id(2L).firstName("B").chatId(2L).isVolunteer(true).phoneNumber("124").build();
    public static final User USER_VOL_C = User.builder().id(3L).firstName("C").chatId(3L).isVolunteer(true).phoneNumber("125").build();
    public static final User USER_VOL_D = User.builder().id(4L).firstName("D").chatId(4L).isVolunteer(true).phoneNumber("126").build();
}
