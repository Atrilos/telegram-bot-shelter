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

    @Query("select u from User u where u.isCatAdopter = true")
    List<User> findByIsCatAdopter(Boolean isAdopter);

    @Query("select u from User u where u.isCatAdopterTrial = true")
    List<User> findByIsCatAdopterTrial(Boolean isAdopter);

    @Query("select u from User u where u.isDogAdopter = true")
    List<User> findByIsDogAdopter(Boolean isAdopter);

    @Query("select u from User u where u.isDogAdopterTrial = true")
    List<User> findByIsDogAdopterTrial(Boolean isAdopter);
}
