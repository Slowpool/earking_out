package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.Achievement;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementTypes;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementDTO;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementExtendedDTO;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.services.app.AchievementService;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.SpringProfiles.WEB;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/inner/v1")
@Profile(WEB)
@AllArgsConstructor
public class InnerApiController {

    private final AchievementService achievementService;
    private final EventStore eventStore;

    // TODO add api key check
    @GetMapping("/unlocked-achievements")
    public ResponseEntity<?> getUnlockedAchievements(@RequestParam final UserId userId) {
        var achievements = achievementService.getUnlockedAchievements(userId, true);
        var body = new HashMap<String, Object>();
        body.put("achievements", achievements);
        return ok(body);
    }

    @GetMapping("/domain-events")
    // TODO create custom orderBy and orderByDirection enums
    public ResponseEntity<?> getDomainEvents(@RequestParam(required = false) final String eventTypes, @RequestParam(required = false) final UserId userId, @RequestParam(required = false) final String orderBy, @RequestParam(required = false) final String orderByDirection) {
        // var domainEvents = eventStore.getAll();
        DomainEvent[] domainEvents = null;
        var body = new HashMap<String, Object>();
        body.put("domainEvents", domainEvents);
        return ok(body);
    }

}
