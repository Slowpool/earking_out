package org.swetlokognatsk.earking_out.achievements_service.infrastructure.adapters.messagebroker;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.UnlockedAchievement;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.messagebroker.MessageBroker;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.achievements_service.app.web.configs.KafkaConfig.ACHIEVEMENTS_TOPIC;

@Component
@AllArgsConstructor
public class KafkaMessageBroker implements MessageBroker {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendNewAchievement(final UnlockedAchievement achievement) {
        kafkaTemplate.send(ACHIEVEMENTS_TOPIC, achievement);
    }
}
