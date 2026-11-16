package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements.FIRST_AUDIO_PERFECT_PITCH_SUCCESS;
import org.junit.jupiter.api.*;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;

public class AchievementsTest {

    private static final UserId USER_ID = new UserId(1);

    @Test
    void unlock() {
        var achievement = FIRST_AUDIO_PERFECT_PITCH_SUCCESS;

        var unlockedAchievement = achievement.unlock(USER_ID);

        assertEquals(achievement.id, unlockedAchievement.id);
        assertEquals(USER_ID, unlockedAchievement.userId);
    }
}
