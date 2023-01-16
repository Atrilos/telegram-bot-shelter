package pro.sky.telegrambotshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambotshelter.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
