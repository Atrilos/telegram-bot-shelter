package pro.sky.telegrambotshelter.exception;

import lombok.extern.slf4j.Slf4j;

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
