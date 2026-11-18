package org.swetlokognatsk.earking_out.core.domain.model.achievements;

import org.swetlokognatsk.earking_out.core.domain.model.base.Entity;

public class Achievement extends Entity<AchievementId> {
    private static final long serialVersionUID = 1L;

    public final AchievementTechnicalName technicalName;

    public Achievement(final AchievementId id, final AchievementTechnicalName technicalName) {
        super(id);
        this.technicalName = technicalName;
    }
}
