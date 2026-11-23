package org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker;

import org.springframework.kafka.annotation.KafkaListener;
import static org.swetlokognatsk.earking_out.app.web.configs.KafkaConfig.ACHIEVEMENTS_TOPIC;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementTechnicalName;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.NewUnlockedAchievement;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.services.app.AchievementService;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;
import lombok.AllArgsConstructor;

@WebComponent
@AllArgsConstructor
public class KafkaAchievementsListener {

    private final AchievementService achievementService;

    @KafkaListener(topics = ACHIEVEMENTS_TOPIC)
    public void consume(final NewUnlockedAchievementDTO newUnlockedAchievement) {
        var id = new AchievementTechnicalName(newUnlockedAchievement.id());
        var userId = new UserId(newUnlockedAchievement.userId());
        var unlockedAchievement = new NewUnlockedAchievement(id, userId, newUnlockedAchievement.unlockedOn());
        achievementService.handleNewUnlockedAchievement(unlockedAchievement);
    }
}
