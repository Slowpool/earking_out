package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@Controller
public class OauthController {

    @GetMapping("/oauth/vk-id/callback")
    public ResponseEntity<?> vkidCallback(final Map<String, Object> body) {
        return ResponseEntity.ok(null);
    }
}
