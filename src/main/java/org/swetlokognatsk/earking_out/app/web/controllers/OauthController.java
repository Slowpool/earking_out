package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.swetlokognatsk.earking_out.core.domain.model.identity.OauthProviders;
import org.swetlokognatsk.earking_out.core.domain.services.app.identity.UserService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;

import java.util.Map;

@Controller
@RequestMapping("/oauth")
public class OauthController {

    // TODO how to verify that this is indeed this user, not a manual request with self-written body?
    @PostMapping("/vk-id/callback")
    public ResponseEntity<?> vkidCallback(@RequestBody final Map<String, String> body) {
        var userId = body.get("userId");
        var avatarUrl = body.get("avatar");
        var userName = body.get("firstName") + " "
        DI.get(UserService.class)
            .attachOauthToCurrentUser(OauthProviders.VKID, userId, avatarUrl);

        
        var userId = DI.get(UserResolver.class)
                .getCurrentUserId();

        var userRepository = DI.get(UserRepository.class);
        var user = userRepository.get(userId);
        return ResponseEntity.ok("{\"bazinga\": true}");
    }
}
