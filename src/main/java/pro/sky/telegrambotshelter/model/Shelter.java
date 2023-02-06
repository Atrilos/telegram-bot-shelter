package pro.sky.telegrambotshelter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @Builder.Default
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
    @Column(name = "map_url", nullable = false)
    private String mapUrl;

    /**
     * Инструкции по правилам безопасности внутри приюта
     */
    @Lob
    @Column(name = "safety_instructions", nullable = false)
    private String safetyInstructions;

    /**
     * Правила знакомства с животными
     */
    @Lob
    @Column(name = "meeting_rules", nullable = false)
    private String meetingRules;

    /**
     * Список документов, чтобы взять животное из приюта
     */
    @Lob
    @Column(name = "papers", nullable = false)
    private String papers;

    /**
     * Рекомендации по транспортировке животного
     */
    @Lob
    @Column(name = "transport", nullable = false)
    private String transport;

    /**
     * Рекомендации по обустройству дома для щенка
     */
    @Lob
    @Column(name = "pup_home")
    private String pupHome;

    /**
     * Рекомендации по обустройству дома для взрослой собаки
     */
    @Lob
    @Column(name = "dog_home")
    private String dogHome;

    /**
     * Рекомендации по обустройству дома собаки с ограниченными возможностями
     */
    @Lob
    @Column(name = "disabled_dog_home")
    private String disabledDogHome;

    /**
     * Рекомендации по обустройству дома для кошки
     */
    @Lob
    @Column(name = "cat_home")
    private String catHome;

    /**
     * Советы по первичному общению с животным
     */
    @Lob
    @Column(name = "communication", nullable = false)
    private String communication;

    /**
     * Рекомендации по проверенным кинологам для дальнейшего обращения к ним
     */
    @Lob
    @Column(name = "dog_handlers")
    private String dogHandlers;

    /**
     * Причины, по которым вам могут отказать забрать животное из приюта
     */
    @Lob
    @Column(name = "refusal_cause", nullable = false)
    private String refusalCause;

    @OneToMany(mappedBy = "shelter")
    private Set<Pet> pets = new HashSet<>();

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
