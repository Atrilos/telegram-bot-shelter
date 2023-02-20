package pro.sky.telegrambotshelter.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pro.sky.telegrambotshelter.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:alpine"));
    @Autowired
    private UserRepository out;
    @Autowired
    private TestEntityManager entityManager;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url=", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username=", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password=", postgreSQLContainer::getPassword);
    }

    @Test
    public void findByChatId() {
        User a = User.builder().chatId(1L).firstName("A").build();

        assertThat(out.findByChatId(1L)).isEqualTo(Optional.empty());
        entityManager.persist(a);
        assertThat(out.findByChatId(1L)).isEqualTo(Optional.of(a)); //equality by natural id, so no need to check id
    }

    @Test
    public void findByIsDogAdopterTrialTrueOrIsCatAdopterTrialTrue() {
        User a = User.builder().chatId(1L).firstName("A").isDogAdopterTrial(true).build();
        User b = User.builder().chatId(2L).firstName("B").isCatAdopterTrial(true).build();
        User c = User.builder().chatId(3L).firstName("C").build();

        entityManager.persist(a);
        assertThat(out.findByIsDogAdopterTrialTrueOrIsCatAdopterTrialTrue())
                .hasSize(1)
                .contains(a);

        entityManager.persist(b);
        assertThat(out.findByIsDogAdopterTrialTrueOrIsCatAdopterTrialTrue())
                .hasSize(2)
                .contains(a, b);

        entityManager.persist(c);
        assertThat(out.findByIsDogAdopterTrialTrueOrIsCatAdopterTrialTrue())
                .doesNotContain(c);
    }

    @Test
    public void findRandomVolunteer() {
        User a = User.builder().chatId(1L).firstName("A").isVolunteer(true).build();
        User b = User.builder().chatId(2L).firstName("B").isVolunteer(true).build();
        User c = User.builder().chatId(3L).firstName("C").isVolunteer(false).build();
        entityManager.persist(a);
        entityManager.persist(b);
        entityManager.persist(c);

        Optional<User> randomVolunteer = out.findRandomVolunteer();

        assertThat(randomVolunteer.isPresent()).isTrue();
        assertThat(randomVolunteer.get().getIsVolunteer()).isTrue();
    }
}