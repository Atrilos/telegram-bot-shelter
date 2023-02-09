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
import pro.sky.telegrambotshelter.model.Pet;
import pro.sky.telegrambotshelter.model.Shelter;
import pro.sky.telegrambotshelter.model.enums.ShelterType;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PetRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:alpine"));
    @Autowired
    private PetRepository out;

    @Autowired
    private TestEntityManager entityManager;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url=", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username=", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password=", postgreSQLContainer::getPassword);
    }

    @Test
    public void findByPetType() {
        Shelter shelter = Shelter.builder()
                .build();
        Pet a = Pet.builder()
                .name("A")
                .petType(ShelterType.DOG)
                .color("AA")
                .shelter(shelter)
                .build();
        Pet b = Pet.builder()
                .name("B")
                .petType(ShelterType.CAT)
                .color("BB")
                .shelter(shelter)
                .build();

        entityManager.persist(shelter);
        entityManager.persist(a);
        entityManager.persist(b);

        assertThat(out.findByPetType(ShelterType.DOG)).hasSize(1).contains(a).doesNotContain(b);
        assertThat(out.findByPetType(ShelterType.CAT)).hasSize(1).contains(b).doesNotContain(a);
    }
}