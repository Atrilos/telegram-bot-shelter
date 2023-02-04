package pro.sky.telegrambotshelter.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum класс для обозначения текущего меню пользователя
 */
@Getter
@RequiredArgsConstructor
public enum CurrentMenu {
    MAIN, SELECT_SHELTER, SHELTER_INFO, ADOPTION, REPORT, CALL_STAFF
}
