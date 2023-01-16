package pro.sky.telegrambotshelter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

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

    @Column(name = "working_hours", nullable = false)
    private String workingHours;

    @Column(name = "map_url")
    private String mapUrl;

    @Lob
    @Column(name = "safety_instructions")
    private String safetyInstructions;

}
