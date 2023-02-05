package pro.sky.telegrambotshelter.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShelterNotFoundException extends RuntimeException {
    public ShelterNotFoundException() {
        super();
    }

    public ShelterNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
