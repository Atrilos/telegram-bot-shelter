package pro.sky.telegrambotshelter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import pro.sky.telegrambotshelter.model.enums.UserState;

import java.util.Objects;

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

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "is_admin")
    private Boolean isAdmin = Boolean.FALSE;

    @Column(name = "is_volunteer")
    private Boolean isVolunteer = Boolean.FALSE;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private UserState state;

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
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
