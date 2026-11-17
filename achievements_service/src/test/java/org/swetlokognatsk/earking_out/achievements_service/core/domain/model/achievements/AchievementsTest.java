package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements;

import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements.*;
import java.util.List;
import org.junit.jupiter.api.*;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEventTypes.*;

public class AchievementsTest {

    private static final UserId USER_ID = new UserId(1);

    @Test
    void unlock() {
        var achievement = FIRST_AUDIO_PERFECT_PITCH_SUCCESS;

        var unlockedAchievement = achievement.unlock(USER_ID);

        assertEquals(achievement.id, unlockedAchievement.id);
        assertEquals(USER_ID, unlockedAchievement.userId);
    }

    @Test
    void getRelatedAchievementsForNotExistentEvent() {
        var relatedAchievements = Achievements.getRelatedAchievements("not-existing-type");

        assertTrue(relatedAchievements.isEmpty());
    }

    @Test
    void getRelatedAchievementsForGuessEvent() {
        var relatedAchievements = Achievements.getRelatedAchievements(GUESS);

        // no strict `relatedAchievements.size() == x` check, because later the number of returned achievements will increase, which may break the test
        assertFalse(relatedAchievements.isEmpty());
        var expectedAchievements = List.of(FIRST_AUDIO_PERFECT_PITCH_SUCCESS);
        assertTrue(relatedAchievements.containsAll(expectedAchievements));
    }

    @Test
    void getRelatedAchievementsForSessionFinishedEvent() {
        var relatedAchievements = Achievements.getRelatedAchievements(SESSION_FINISHED);

        // no strict `relatedAchievements.size() == x` check, because later the number of returned achievements will increase, which may break the test
        assertFalse(relatedAchievements.isEmpty());
        var expectedAchievements = List.of(FINISH_10_AUDIO_PERFECT_PITCH_SESSIONS, FINISH_100_AUDIO_PERFECT_PITCH_SESSIONS);
        assertTrue(relatedAchievements.containsAll(expectedAchievements));
    }
}
