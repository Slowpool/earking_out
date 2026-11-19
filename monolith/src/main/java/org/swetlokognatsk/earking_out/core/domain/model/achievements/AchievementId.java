package org.swetlokognatsk.earking_out.core.domain.model.achievements;

import java.io.Serializable;
import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AchievementId extends ValueObject implements Serializable {
    public final UUID id;

    public static AchievementId random() {
        return new AchievementId(UUID.randomUUID());
    }
}
