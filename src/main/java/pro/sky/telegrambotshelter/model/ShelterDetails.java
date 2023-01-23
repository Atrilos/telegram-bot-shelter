package pro.sky.telegrambotshelter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * Entity-класс, описывающий данные по обслуживаемым приютам для животных
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Slf4j
@Table(name = "shelter_details")
public class ShelterDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Рабочие часы приюта
     */
    @Column(name = "working_hours", nullable = false)
    private String workingHours;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ShelterDetails that = (ShelterDetails) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
