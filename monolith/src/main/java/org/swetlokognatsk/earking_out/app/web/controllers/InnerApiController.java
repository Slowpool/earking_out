package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementTypes;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.services.app.AchievementService;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.SpringProfiles.WEB;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/inner/v1")
@Profile(WEB)
@AllArgsConstructor
public class InnerApiController {

    private final AchievementService achievementService;
    private final EventStore eventStore;

    // TODO add api key check
    @GetMapping("/achievements")
    public ResponseEntity<?> getAchievements(@RequestParam final AchievementTypes type, @RequestParam(required = false) final UserId userId) {
        var achievements = achievementService.get(type, userId);
        var body = new HashMap<String, Object>();
        body.put("achievements", achievements);
        return ok(body);
    }

    @GetMapping("/domain-events")
    public ResponseEntity<?> getDomainEvents(@RequestParam final AchievementTypes type, @RequestParam(required = false) final UserId userId) {
        // var domainEvents = eventStore.getAll();
        DomainEvent[] domainEvents = null;
        var body = new HashMap<String, Object>();
        body.put("domainEvents", domainEvents);
        return ok(body);
    }

}
