package org.swetlokognatsk.earking_out;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.swetlokognatsk.earking_out.core.ports.achievements.UnlockedAchievementsRepository;
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
}
