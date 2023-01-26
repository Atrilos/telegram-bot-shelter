package pro.sky.telegrambotshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambotshelter.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.chatId = ?1")
    Optional<User> findByChatId(Long chatId);

    @Query("select u from User u where u.isVolunteer = true")
    List<User> findVolunteers();

}
