package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements;

import java.util.Set;

import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEventTypes;

public enum Achievements {
    ACHIEVEMENT_1(new AchievementId("1"), new String[] { DomainEventTypes.NEW_PUZZLE_EVENT }),
    ACHIEVEMENT_2(new AchievementId("2"), new String[] { DomainEventTypes.NEW_PUZZLE_EVENT });

    public final AchievementId id;
    public final String[] triggerEventTypes;

    private Achievements(final AchievementId id, final String[] triggerEventTypes) {
        this.id = id;
        this.triggerEventTypes = triggerEventTypes;
    }

    public static Set<Achievements> getRelatedAchievements(final String triggerEventType) {
        // TODO filter
        return Set.of(ACHIEVEMENT_1, ACHIEVEMENT_2);
    }
}
