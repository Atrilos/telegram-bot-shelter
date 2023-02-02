package pro.sky.telegrambotshelter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;

import java.util.Objects;

/**
 * Entity-класс, описывающий пользователя, обратившегося к боту
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Slf4j
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Уникальный идентификатор чата
     */
    @NaturalId
    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    /**
     * Имя пользователя в соответствии с обязательным полем в классе {@link com.pengrad.telegrambot.model.User User}
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Boolean-флаг наличия прав администратора
     */
    @Column(name = "is_admin")
    private Boolean isAdmin = Boolean.FALSE;

    /**
     * Boolean-флаг наличия прав волонтера
     */
    @Column(name = "is_volunteer")
    private Boolean isVolunteer = Boolean.FALSE;

    /**
     * Boolean-флаг для тех, кто взял собаку из приюта
     */
    @Column(name = "is_adopter")
    private Boolean isAdopter = Boolean.FALSE;

    /**
     * Номер телефона пользователя. Обязательное поле при передаче пользователя в качестве контакта волонтеру.
     */
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    /**
     * День последнего текстового отчета пользователя.
     */
    @Column(name = "last_report_day")
    private int lastReportDay;

    /**
     * День последнего фото отчета пользователя.
     */
    @Column(name = "last_photo_report_day")
    private int lastPhotoReportDay;

    /**
     * День когда животное забрали из приюта.
     */
    @Column(name = "adoption_day")
    private int adoptionDay;

    /**
     * Испытательный период пройден, животное остается в новом доме.
     */
    @Column(name = "adoption_approved")
    private boolean adoptionApproved;

    /**
     * Текущее меню, в котором находится пользователь.
     */
    @Column(name = "current_menu")
    @Enumerated(EnumType.STRING)
    private CurrentMenu currentMenu;

    @PostPersist
    public void logUserAdded() {
        log.info(
                "Added user: chatId={}, phoneNumber={}, first_name={}",
                chatId,
                phoneNumber,
                firstName
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return chatId != null && Objects.equals(chatId, user.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }
}
