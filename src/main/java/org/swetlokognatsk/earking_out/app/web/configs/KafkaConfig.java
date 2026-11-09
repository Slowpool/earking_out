package org.swetlokognatsk.earking_out.app.web.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import static org.swetlokognatsk.earking_out.SpringProfiles.WEB;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
@Profile(WEB)
@EnableKafka
public class KafkaConfig {

    @Bean
    NewTopics domainEventsTopic() {
        return new NewTopics(
                new NewTopic("domain-events", 3, (short) 1),
                new NewTopic("achievements", 3, (short) 1));
    }
}
