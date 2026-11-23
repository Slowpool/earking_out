package org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker;

import java.io.Serializable;
import java.time.LocalDateTime;

public record NewUnlockedAchievementDTO(String id, int userId, LocalDateTime unlockedOn) implements Serializable {
    private static final long serialVersionUID = 1L;

}
