package pro.sky.telegrambotshelter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;
import pro.sky.telegrambotshelter.model.enums.CurrentMenu;
import pro.sky.telegrambotshelter.model.enums.ShelterType;

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
    @Builder.Default
    private Boolean isAdmin = Boolean.FALSE;

    /**
     * Boolean-флаг наличия прав волонтера
     */
    @Column(name = "is_volunteer")
    @Builder.Default
    private Boolean isVolunteer = Boolean.FALSE;

    /**
     * Boolean-флаг для тех, кто взял собаку из приюта с испытательным сроком
     */
    @Column(name = "is_dog_adopter_trial")
    @Builder.Default
    private Boolean isDogAdopterTrial = Boolean.FALSE;


    /**
     * Boolean-флаг для тех, кто взял собаку из приюта
     */
    @Column(name = "is_dog_adopter")
    @Builder.Default
    private Boolean isDogAdopter = Boolean.FALSE;

    /**
     * Boolean-флаг для тех, кто взял кошку из приюта с испытательным сроком
     */
    @Column(name = "is_cat_adopter_trial")
    @Builder.Default
    private Boolean isCatAdopterTrial = Boolean.FALSE;


    /**
     * Boolean-флаг для тех, кто взял кошку из приюта
     */
    @Column(name = "is_cat_adopter")
    @Builder.Default
    private Boolean isCatAdopter = Boolean.FALSE;

    /**
     * Номер телефона пользователя. Обязательное поле при передаче пользователя в качестве контакта волонтеру.
     */
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    /**
     * День последнего текстового отчета пользователя.
     */
    @Column(name = "last_report_day")
    private Integer lastReportDay;

    /**
     * День последнего фото отчета пользователя.
     */
    @Column(name = "last_photo_report_day")
    private Integer lastPhotoReportDay;

    /**
     * День когда животное забрали из приюта.
     */
    @Column(name = "adoption_day")
    private Integer adoptionDay;

    /**
     * Текущее меню, в котором находится пользователь.
     */
    @Column(name = "current_menu")
    @Enumerated(EnumType.STRING)
    private CurrentMenu currentMenu = CurrentMenu.MAIN;

    @Column(name = "current_shelter")
    @Enumerated(EnumType.STRING)
    private ShelterType currentShelter;

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
