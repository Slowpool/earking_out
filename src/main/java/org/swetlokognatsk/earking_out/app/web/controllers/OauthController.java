package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.view.RedirectView;
import static org.swetlokognatsk.earking_out.app.web.SessionAttributes.*;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.DuplicateOauthAuthenticationException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthProviders;
import org.swetlokognatsk.earking_out.core.domain.services.app.identity.UserService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import static org.springframework.http.ResponseEntity.*;

@Controller
@RequestMapping("/oauth")
public class OauthController {
    // TODO move to .env
    public static final String CLIENT_ID = "54834142";
    public static final String VKID_CALLBACK_ENDPOINT = "/vk-id/callback";

    // TODO refactoring
    @PostMapping(VKID_CALLBACK_ENDPOINT)
    public ResponseEntity<?> postVkidCallback(@RequestBody final Map<String, String> body, final HttpSession session) {
        var code = body.get("code");
        var state = body.get("state");
        var deviceId = body.get("deviceId");
        var codeVerifier = body.get("codeVerifier");

        try {
            return vkidCallback(code, state, deviceId, codeVerifier, session);
        } catch (DuplicateOauthAuthenticationException e) {
            return status(409)
                    .body("attempt to login under the same oauth provider twice: " + e.provider.name());
        }
    }

    @GetMapping(VKID_CALLBACK_ENDPOINT)
    public RedirectView getVkidCallback(@RequestParam final Map<String, String> requestParams, final HttpSession session) {
        var code = requestParams.get("code");
        var state = requestParams.get("state");
        var deviceId = requestParams.get("device_id");
        var codeVerifier = requestParams.get("code_verifier");

        try {
            vkidCallback(code, state, deviceId, codeVerifier, session);
        } catch (DuplicateOauthAuthenticationException e) {
            // TODO show `you already authorized` message
            return new RedirectView("/");
        } catch (Throwable e) {
            // TODO show `something went wrong, try again` message
            return new RedirectView("/");
        }

        return new RedirectView("/");
    }

    private ResponseEntity<?> vkidCallback(final String code, final String state, final String deviceId, final String codeVerifier, final HttpSession session) throws DuplicateOauthAuthenticationException {
        var tokens = exchangeVkidCode(code, state, deviceId, codeVerifier);
        var accessToken = (String) tokens.get("access_token");
        var userInfo = getUserInfo(accessToken);

        var vkUserId = userInfo.get("user_id");
        var avatarUrl = userInfo.get("avatar");
        var userName = "%s %s".formatted(userInfo.get("first_name"), userInfo.get("last_name"));

        var actualUserId = DI.get(UserService.class)
                .ensureUserIsAttachedToOauth(OauthProviders.VKID, vkUserId, userName, avatarUrl);
        if (actualUserId.isPresent()) {
            // TODO also save language to session
            saveUserToSession(session, actualUserId.get());
        }

        return ok("{\"success\": true}");
    }

    private void saveUserToSession(final HttpSession session, final UserId actualUserId) {
        session.setAttribute(USER_ID, actualUserId);
        session.removeAttribute(LOCALE);
    }

    private Map<String, Object> exchangeVkidCode(final String code, final String state, final String deviceId,
            final String codeVerifier) {
        var serviceId = DI.get(Environment.class)
                .getProperty("VKID_SERVICE_ID");

        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("code_verifier", codeVerifier);
        body.add("redirect_uri", "https://earking-out.ru/oauth/vk-id/callback");
        body.add("code", code);
        body.add("service_token", serviceId);
        body.add("client_id", CLIENT_ID);
        body.add("device_id", deviceId);
        body.add("state", state);

        var restClient = RestClient.create();
        var responseBody = restClient.post()
                .uri("https://id.vk.com/oauth2/auth")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(Map.class);

        return responseBody;
    }

    private Map<String, String> getUserInfo(final String accessToken) {
        var body = new LinkedMultiValueMap<String, String>();

        body.add("access_token", accessToken);
        body.add("client_id", CLIENT_ID);

        var restClient = RestClient.create();
        var responseBody = restClient.post()
                .uri("https://id.vk.com/oauth2/user_info")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(Map.class);

        return (Map<String, String>) responseBody.get("user");
    }
}
