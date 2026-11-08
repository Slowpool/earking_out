package org.swetlokognatsk.earking_out.app.web.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import static org.swetlokognatsk.earking_out.SpringProfiles.WEB;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
@Profile(WEB)
public class KafkaConfig {

    NewTopic domainEventsTopic() {

    }
}
