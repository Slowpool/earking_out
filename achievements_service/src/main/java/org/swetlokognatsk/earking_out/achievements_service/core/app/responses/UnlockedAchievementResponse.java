package org.swetlokognatsk.earking_out.achievements_service.core.app.responses;

import java.util.Map;
import java.util.Set;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.UnlockedAchievement;

public record UnlockedAchievementResponse(Set<Map<String,Object>> achievements) {

}
