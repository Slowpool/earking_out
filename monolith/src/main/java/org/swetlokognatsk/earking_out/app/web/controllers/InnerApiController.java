package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementTypes;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import static org.swetlokognatsk.earking_out.SpringProfiles.WEB;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/inner/v1")
@Profile(WEB)
public class InnerApiController {

    // TODO add api key check
    @GetMapping("/achievements")
    public ResponseEntity<?> getAchievements(@RequestParam final AchievementTypes type, @RequestParam(required = false) final UserId userId) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/domain-events")
    public ResponseEntity<?> getDomainEvents(@RequestParam final AchievementTypes type, @RequestParam(required = false) final UserId userId) {
        return ResponseEntity.ok(null);
    }

}
