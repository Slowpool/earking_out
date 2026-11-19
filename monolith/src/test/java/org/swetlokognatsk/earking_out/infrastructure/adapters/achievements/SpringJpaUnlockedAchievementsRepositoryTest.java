package org.swetlokognatsk.earking_out.infrastructure.adapters.achievements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.BEFORE_METHOD;
import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import static org.swetlokognatsk.earking_out.TestHelper.*;
import java.time.LocalDateTime;
import java.util.UUID;

// TODO what does it do
@Testcontainers
@DirtiesContext(methodMode = BEFORE_METHOD)
@EOSpringBootTest
@EnableAutoConfiguration(exclude = {})
@ActiveProfiles({ TEST, JPA })
public class SpringJpaUnlockedAchievementsRepositoryTest {

    // TODO what does it do
    @Container
    // TODO what does it do
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18.6");

    private SpringJpaUnlockedAchievementsRepository repository;

    private UnlockedAchievementAggregate newUnlockedAchievementAggregate() {
        return new UnlockedAchievementAggregate(UnlockedAchievementId.random(), USER_ID, AchievementId.random(), LocalDateTime.now());
    }

    @BeforeEach
    public void setup() {
        repository = DI.get(SpringJpaUnlockedAchievementsRepository.class);
    }

    @Test
    void existsWhenDoesNotExist() {
        var exists = repository.exists(USER_ID, AchievementId.random());

        assertFalse(exists);
    }

    @Test
    void existsWhenExists() {
        var unlockedAchievement = newUnlockedAchievementAggregate();
        repository.save(unlockedAchievement);

        var exists = repository.exists(unlockedAchievement.userId, unlockedAchievement.achievementId);

        assertTrue(exists);
    }

    @Test
    void getByIdNotExisting() {
        try {
            repository.get(UnlockedAchievementId.random());
            fail();
        } catch (AggregateNotFoundException e) {
        }
    }

    @Test
    void getByUserAndAchievementNotExisting() {
        try {
            repository.get(USER_ID, AchievementId.random());
            fail();
        } catch (AggregateNotFoundException e) {
        }
    }

    @Test
    void getExistingById() {
        var unlockedAchievement = newUnlockedAchievementAggregate();
        repository.save(unlockedAchievement);

        var aggregate = repository.get(unlockedAchievement.getId());

        assertNotNull(aggregate);
        assertEquals(unlockedAchievement.userId, aggregate.userId);
        assertEquals(unlockedAchievement.achievementId, aggregate.achievementId);
    }

    @Test
    void getExistingByUserAndAchievement() {
        var unlockedAchievement = newUnlockedAchievementAggregate();
        repository.save(unlockedAchievement);

        var aggregate = repository.get(unlockedAchievement.userId, unlockedAchievement.achievementId);

        assertNotNull(aggregate);
        assertEquals(unlockedAchievement.userId, aggregate.userId);
        assertEquals(unlockedAchievement.achievementId, aggregate.achievementId);
    }
}
