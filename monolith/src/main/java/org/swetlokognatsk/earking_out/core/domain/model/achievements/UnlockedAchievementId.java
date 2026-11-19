package org.swetlokognatsk.earking_out.core.domain.model.achievements;

import java.io.Serializable;
import java.util.UUID;

// TODO how to mark it nullable
public record UnlockedAchievementId(UUID id) implements Serializable {
    private static final long serialVersionUID = 1L;

    public static UnlockedAchievementId random() {
        return new UnlockedAchievementId(UUID.randomUUID());
    }
}
