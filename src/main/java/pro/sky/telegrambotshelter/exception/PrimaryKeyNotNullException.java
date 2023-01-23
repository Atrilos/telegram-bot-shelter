package pro.sky.telegrambotshelter.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Runtime-исключение при попытке обновления пользователя, используя сущность с null-значением в поле id
 */
@Slf4j
public class PrimaryKeyNotNullException extends RuntimeException {
    public PrimaryKeyNotNullException() {
        super();
    }

    public PrimaryKeyNotNullException(String message) {
        super(message);
        log.error(message);
    }
}
