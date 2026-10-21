package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.swetlokognatsk.earking_out.app.web.services.UriBuilder;
import org.swetlokognatsk.earking_out.core.domain.model.identity.OauthProviders;
import org.swetlokognatsk.earking_out.core.domain.services.app.identity.UserService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/oauth")
public class OauthController {

    // TODO refactoring
    @PostMapping("/vk-id/callback")
    public ResponseEntity<?> vkidCallback(@RequestBody final Map<String, String> body) {
        var code = body.get("code");
        var state = body.get("state");
        var deviceId = body.get("deviceId");
        var codeVerifier = body.get("codeVerifier");

        // var tokens = exchangeVkidCode(code, state, deviceId);
        // var accessToken = tokens.get("accessToken");
        // var userInfo = getUserInfo(accessToken);
        
        // var vkUserId = userInfo.get("user_id");
        // var avatarUrl = userInfo.get("avatar");
        // var userName = "%s %s".formatted(userInfo.get("first_name"), userInfo.get("last_name"));

        // DI.get(UserService.class)
        //         .ensureUserIsAttachedToOauth(OauthProviders.VKID, vkUserId, userName, avatarUrl);

        return ResponseEntity.ok("{\"success\": true}");
    }

    // private Map<String,String> exchangeVkidCode(final String code, final String state, final String deviceId, final String codeVerifier) {
    //     var body = new HashMap<String, String>();
    //     body.put("grant_type", "authorization_code");
    //     body.put("code_verifier", codeVerifier);

    //     var uri = URI.create("https://id.vk.com/oauth2/auth");
    //     var httpRequest = HttpRequest.newBuilder(uri);
    // }
}
