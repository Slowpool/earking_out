package org.swetlokognatsk.earking_out.infrastructure.adapters.achievements;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.BEFORE_METHOD;
import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.TestUserResolver;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.testcontainers.postgresql.PostgreSQLContainer;
import static org.swetlokognatsk.earking_out.TestHelper.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import javax.sql.DataSource;

// TODO still there are ways to fail some tests. more tests can be written
@DirtiesContext(methodMode = BEFORE_METHOD)
@EOSpringBootTest
@EnableAutoConfiguration(exclude = {})
@ActiveProfiles({ TEST, JPA })
@Transactional
public class SpringJpaUnlockedAchievementsRepositoryTest {

    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18.6");

    private SpringJpaUnlockedAchievementsRepository repository;
    private TestUserResolver userResolver;

    private UnlockedAchievementAggregate newUnlockedAchievementAggregate(final String uuid) {
        var achievementId = new AchievementId(UUID.fromString(uuid));
        return new UnlockedAchievementAggregate(UnlockedAchievementId.empty(), userResolver.getCurrentUserId(), achievementId, LocalDateTime.now(), null);
    }

    private UnlockedAchievementAggregate newUnlockedAchievementAggregate() {
        return newUnlockedAchievementAggregate(ACHIEVEMENT_ID_1);
    }

    private void assertAchievementsEqual(final UnlockedAchievementAggregate expectedAchievement, final UnlockedAchievementAggregate achievement) {
        assertEquals(expectedAchievement.userId, achievement.userId);
        assertEquals(expectedAchievement.achievementId, achievement.achievementId);
    }

    private void assertContainsOnlyThisAchievement(final Set<UnlockedAchievementAggregate> achievements, final UnlockedAchievementAggregate expectedAchievement) {
        assertEquals(1, achievements.size());
        var achievement = achievements.iterator()
                .next();
        assertAchievementsEqual(expectedAchievement, achievement);
    }

    private void assertContainsOnlyTheseAchievements(final Set<UnlockedAchievementAggregate> achievements, final Set<UnlockedAchievementAggregate> expectedAchievements) {
        assertEquals(expectedAchievements.size(), achievements.size());
        for (var expectedAchievement : expectedAchievements) {
            var contains = achievements.stream()
                    .anyMatch(sourceAchievement -> {
                        try {
                            assertAchievementsEqual(expectedAchievement, sourceAchievement);
                            return true;
                        } catch (AssertionError e) {
                            return false;
                        }
                    });
            if (!contains) {
                fail();
            }
        }
    }

    @BeforeEach
    public void setup() {
        repository = DI.get(SpringJpaUnlockedAchievementsRepository.class);
        userResolver = DI.get(TestUserResolver.class);
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
        var id = repository.saveAndGetId(unlockedAchievement);

        var aggregate = repository.get(id);

        assertNotNull(aggregate);
        assertAchievementsEqual(unlockedAchievement, aggregate);
    }

    @Test
    void getExistingByUserAndAchievement() {
        var unlockedAchievement = newUnlockedAchievementAggregate();
        repository.save(unlockedAchievement);

        var aggregate = repository.get(unlockedAchievement.userId, unlockedAchievement.achievementId);

        assertNotNull(aggregate);
        assertAchievementsEqual(unlockedAchievement, aggregate);
    }

    @Test
    void getByUserIdZeroAchievements() {
        var achievements = repository.get(USER_ID);

        assertTrue(achievements.isEmpty());
    }

    @Test
    void getByUserIdOneAchievement() {
        var unlockedAchievement = newUnlockedAchievementAggregate();
        repository.save(unlockedAchievement);

        var achievements = repository.get(USER_ID);

        assertContainsOnlyThisAchievement(achievements, unlockedAchievement);
    }

    @Test
    void getByUserIdOneAchievementFilteringAlienAchievement() {
        var unlockedAchievement = newUnlockedAchievementAggregate();
        repository.save(unlockedAchievement);
        switchToAnotherUser();
        //
        var alienAchievement = newUnlockedAchievementAggregate();
        repository.save(alienAchievement);
        switchToHomeUser();

        var achievements = repository.get(USER_ID);

        assertContainsOnlyThisAchievement(achievements, unlockedAchievement);
    }

    @Test
    void getByUserIdTwoAchievements() {
        var unlockedAchievement1 = newUnlockedAchievementAggregate(ACHIEVEMENT_ID_1);
        repository.save(unlockedAchievement1);
        var unlockedAchievement2 = newUnlockedAchievementAggregate(ACHIEVEMENT_ID_2);
        repository.save(unlockedAchievement2);

        var achievements = repository.get(USER_ID);

        assertContainsOnlyTheseAchievements(achievements, Set.of(unlockedAchievement1, unlockedAchievement2));
    }
}
