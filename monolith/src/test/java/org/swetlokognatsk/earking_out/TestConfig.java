package org.swetlokognatsk.earking_out;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.swetlokognatsk.earking_out.core.ports.achievements.UnlockedAchievementsRepository;
import org.swetlokognatsk.earking_out.core.ports.events.messagebroker.MessageBroker;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import static org.swetlokognatsk.earking_out.SpringProfiles.TEST;
import org.mockito.Mockito;

@Configuration
@Profile(TEST)
public class TestConfig {

    @Bean
    @ConditionalOnMissingBean
    UnlockedAchievementsRepository unlockedAchievementsRepository() {
        return Mockito.mock(UnlockedAchievementsRepository.class);
    }

    @Bean
    @ConditionalOnMissingBean
    EventStore eventStore() {
        return Mockito.mock(EventStore.class);
    }

    @Bean
    @ConditionalOnMissingBean
    MessageBroker messageBroker() {
        return Mockito.mock(MessageBroker.class);
    }

    @Bean
    @ConditionalOnMissingBean
    UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }
}
