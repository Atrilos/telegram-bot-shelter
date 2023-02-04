package pro.sky.telegrambotshelter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * Entity-класс, описывающий данные по обслуживаемым приютам для собак
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Slf4j
@Table(name = "shelter_details")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * приют для кошгк
     */
    @Column(name = "is_cat_Shelter")
    private Boolean isCatShelter = Boolean.FALSE;

    /**
     * о приюте
     */
    @Column(name = "about", nullable = false)
    private String about;
    /**
     * Рабочие часы приюта
     */
    @Column(name = "open_hours", nullable = false)
    private String openHours;

    /**
     * Ссылка на карту с местоположением приюта
     */
    @Column(name = "map_url")
    private String mapUrl;

    /**
     * Инструкции по правилам безопасности внутри приюта
     */
    @Lob
    @Column(name = "safety_instructions")
    private String safetyInstructions;

    /**
     * Правила знакомства с животными
     */
    @Column(name = "meet", nullable = false)
    private String meet;

    /**
     * Список документов, чтобы взять животное из приюта
     */
    @Column(name = "papers", nullable = false)
    private String papers;

    /**
     * Рекомендации по транспортировке животного
     */
    @Column(name = "transport", nullable = false)
    private String transport;

    /**
     * Рекомендации по обустройству дома для щенка
     */
    @Column(name = "pup_home", nullable = false)
    private String pupHome;

    /**
     * Рекомендации по обустройству дома для взрослой собаки
     */
    @Column(name = "dog_home", nullable = false)
    private String dogHome;

    /**
     * Рекомендации по обустройству дома для взрослой собаки
     */
    @Column(name = "disabled_dog_home", nullable = false)
    private String disabledDogHome;

    /**
     * Рекомендации по обустройству дома для кошки
     */
    @Column(name = "cat_home", nullable = false)
    private String catHome;

    /**
     * Советы по первичному общению с животным
     */
    @Column(name = "communication", nullable = false)
    private String communication;

    /**
     * Рекомендации по проверенным кинологам для дальнейшего обращения к ним
     */
    @Column(name = "dog_handlers", nullable = false)
    private String dogHandlers;

    /**
     * Причины, по которым вам могут отказать забрать животное из приюта
     */
    @Column(name = "refusal_cause", nullable = false)
    private String refusalCause;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Shelter that = (Shelter) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
