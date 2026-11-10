package org.swetlokognatsk.earking_out.achievements_service.core.ports.messagebroker;

import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.UnlockedAchievement;

public interface MessageBroker {

    void sendNewAchievement(UnlockedAchievement achievement);
}
