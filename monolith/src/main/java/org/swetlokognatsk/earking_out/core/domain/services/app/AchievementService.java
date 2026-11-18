package org.swetlokognatsk.earking_out.core.domain.services.app;

import java.util.Set;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementTypes;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.NewUnlockedAchievement;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.achievements.UnlockedAchievementsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AchievementService {

    final private UnlockedAchievementsRepository repository;

    public Set<?> get(final AchievementTypes type, final UserId userId) {
        return switch (type) {
        case UNLOCKED -> getUnlockedAchievements(userId);
        default -> throw new IllegalArgumentException("unknown achievement type: " + type.toString());
        };
    }

    private Set<UnlockedAchievementAggregate> getUnlockedAchievements(final UserId userId) {

    }

    public void handleNewUnlockedAchievement(final NewUnlockedAchievement newUnlockedAchievement) {
        if (alreadyPersisted(newUnlockedAchievement)) {
            return;
        }

        repository.save(newUnlockedAchievement);
    }

    private boolean alreadyPersisted(final NewUnlockedAchievement newUnlockedAchievement) {
        return repository.exists(newUnlockedAchievement.userId(), newUnlockedAchievement.id());
    }
}
