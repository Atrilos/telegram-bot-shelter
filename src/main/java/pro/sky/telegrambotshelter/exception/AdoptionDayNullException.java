package pro.sky.telegrambotshelter.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdoptionDayNullException extends RuntimeException{
    public AdoptionDayNullException() {
        super();
    }

    public AdoptionDayNullException(String message) {
        super(message);
        log.error(message);
    }
}
