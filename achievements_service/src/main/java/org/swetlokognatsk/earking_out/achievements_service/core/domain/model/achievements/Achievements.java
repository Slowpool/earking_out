package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements;

import java.util.Set;

import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;

import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEventTypes.*;

// why not store them in persistent storage? because each achievement has its own business logic (custom code), consequently achievements had better to be hardcoded. also, there's no remove/create achievement logic without corresponding achievement code modification
public enum Achievements {
    FIRST_AUDIO_PERFECT_PITCH_SUCCESS(new AchievementId("first_audio_perfect_pitch_success"), new String[] { NEW_PUZZLE }),
    FINISH_100_AUDIO_PERFECT_PITCH_SESSIONS(new AchievementId("finish_100_audio_perfect_pitch_sessions"), new String[] { SESSION_FINISHED }),
    FINISH_10_AUDIO_PERFECT_PITCH_SESSIONS(new AchievementId("finish_10_audio_perfect_pitch_sessions"), new String[] { SESSION_FINISHED });

    public final AchievementId id;
    public final String[] triggerEventTypes;

    private Achievements(final AchievementId id, final String[] triggerEventTypes) {
        this.id = id;
        this.triggerEventTypes = triggerEventTypes;
    }

    public static Set<Achievements> getRelatedAchievements(final String triggerEventType) {
        // TODO filter
        return Set.of(FIRST_AUDIO_PERFECT_PITCH_SUCCESS, FINISH_100_AUDIO_PERFECT_PITCH_SESSIONS);
    }

    public UnlockedAchievement unlock(final UserId userId) {
        return new UnlockedAchievement(id, userId);
    }
}
