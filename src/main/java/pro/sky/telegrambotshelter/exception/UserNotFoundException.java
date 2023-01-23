package pro.sky.telegrambotshelter.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Runtime-исключение для случая получения из БД несуществующего пользователя
 */
@Slf4j
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
