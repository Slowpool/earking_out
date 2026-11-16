package org.swetlokognatsk.earking_out.achievements_service.core.domain.services.domain;

import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements.*;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEventTypes.SESSION_FINISHED;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.UnlockedAchievement;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvent;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvents;
import jakarta.annotation.Nullable;

// TODO architectural refactoring. a lots of optimization ways exist also
@Service
public class AchievementsUnlockerService {

    private static final String PERFECT_PITCH = "PERFECT_PITCH";
    private static final String AUDIO = "AUDIO";

    public Set<UnlockedAchievement> tryUnlock(final Set<Achievements> lockedAchievements, final DomainEvents source, final DomainEvent trigger) {
        var unlockedAchievements = new HashSet<UnlockedAchievement>();
        UnlockedAchievement achievement;
        for (var lockedAchievement : lockedAchievements) {
            achievement = tryUnlock(lockedAchievement, source, trigger);
            if (achievement != null) {
                unlockedAchievements.add(achievement);
            }
        }
        return unlockedAchievements;
    }

    // TODO what is nullable context and how to enable it
    public @Nullable UnlockedAchievement tryUnlock(final Achievements lockedAchievement, final DomainEvents source, final DomainEvent trigger) {
        return switch (lockedAchievement) {
        case FIRST_AUDIO_PERFECT_PITCH_SUCCESS -> tryUnlockFirstAudioPerfectPitchSuccess(source, trigger);
        case FINISH_10_AUDIO_PERFECT_PITCH_SESSIONS -> tryUnlockFinishXAudioPerfectPitchSessions(source, trigger, 10, FINISH_10_AUDIO_PERFECT_PITCH_SESSIONS);
        case FINISH_100_AUDIO_PERFECT_PITCH_SESSIONS -> tryUnlockFinishXAudioPerfectPitchSessions(source, trigger, 100, FINISH_100_AUDIO_PERFECT_PITCH_SESSIONS);
        default -> throw new RuntimeException("unknown achievement: " + lockedAchievement.name());
        };
    }

    private @Nullable UnlockedAchievement tryUnlockFirstAudioPerfectPitchSuccess(final DomainEvents source, final DomainEvent trigger) {
        // TODO ensure this is the first success in `source`
        return (boolean) trigger.get("success")
                ? FIRST_AUDIO_PERFECT_PITCH_SUCCESS.unlock(trigger.userId())
                : null;
    }

    private @Nullable UnlockedAchievement tryUnlockFinishXAudioPerfectPitchSessions(final DomainEvents source, final DomainEvent trigger, final int x, final Achievements achievement) {
        var numberOfStartedSessions = source.get(SESSION_FINISHED)
                .stream()
                // achievement will be given only after x + 1 sessionStarted events. why? because sessionFinishedEvent does not contain exercise info
                // TODO upd: actually, source should contian all SessionStartedEvents. fix this weird sessionStarted logic
                .filter(this::isAudioPerfectPitchSessionFinishedEvent)
                .filter(event -> event.userId().equals(trigger.userId()))
                .count();
        return numberOfStartedSessions >= x + 1
                ? achievement.unlock(trigger.userId())
                : null;
    }

    private boolean isAudioPerfectPitchSessionFinishedEvent(final DomainEvent sessionStartedEvent) {
        // {"name":"PERFECT_PITCH","type":"AUDIO"}
        return sessionStartedEvent.type()
                .equals(SESSION_FINISHED)
                && sessionStartedEvent.get("puzzleConfigDto.exercise.name")
                        .equals(PERFECT_PITCH)
                && sessionStartedEvent.get("puzzleConfigDto.exercise.type")
                        .equals(AUDIO);
    }
}
