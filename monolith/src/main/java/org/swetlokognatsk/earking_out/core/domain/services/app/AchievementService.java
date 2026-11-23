package org.swetlokognatsk.earking_out.core.domain.services.app;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementTypes;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementDTO;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.achievements.UnlockedAchievementDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.achievements.UnlockedAchievementsRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker.NewUnlockedAchievementDTO;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.NewUnlockedAchievement;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AchievementService {

    final private UnlockedAchievementsRepository repository;
    final private UnlockedAchievementAggregatesFactory aggregatesFactory;

    public <UADTO extends UnlockedAchievementDTO> Set<UADTO> getUnlockedAchievements(final UserId userId, final boolean loadAchievements) {
        return (Set<UADTO>) (loadAchievements
                ? repository.getDto(userId)
                : repository.getDtoWithAchievements(userId));
    }

    public void handleNewUnlockedAchievement(final NewUnlockedAchievement newUnlockedAchievement) {
        if (alreadyPersisted(newUnlockedAchievement)) {
            return;
        }

        var achievementId = repository.getAchievementId(newUnlockedAchievement.id());
        var aggregate = aggregatesFactory.create(UnlockedAchievementId.empty(), newUnlockedAchievement.userId(), achievementId, newUnlockedAchievement.unlockedOn());
        var unlockedAchievementId = repository.saveAndGetId(aggregate);

        // TODO send to websockets
    }

    private boolean alreadyPersisted(final NewUnlockedAchievement newUnlockedAchievement) {
        return repository.exists(newUnlockedAchievement.userId(), newUnlockedAchievement.id());
    }
}
