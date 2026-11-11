package org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker;

import org.springframework.kafka.annotation.KafkaListener;
import static org.swetlokognatsk.earking_out.app.web.configs.KafkaConfig.ACHIEVEMENTS_TOPIC;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.NewUnlockedAchievement;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;

@WebComponent
public class KafkaAchievementsListener {

    @KafkaListener(topics = ACHIEVEMENTS_TOPIC)
    public void consume(final NewUnlockedAchievement newUnlockedAchievement) {
        System.out.println("consuming achievement: " + newUnlockedAchievement.toString());
        // TODO consume achievement
    }
}
