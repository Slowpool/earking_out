package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import jakarta.servlet.http.Cookie;
import org.springframework.session.MapSession;

@Controller
@AllArgsConstructor
public class MainController {

    // EOSESSIONID = Earking Out Session ID
    private static final String SESSION_COOKIE = "EOSESSIONID";

    private final SessionRepository<MapSession> sessionRepository;

    @GetMapping("/")
    public String home(@CookieValue(name = SESSION_COOKIE, required = false) String eoSessionId, final HttpServletResponse response) {
        eoSessionId = ensureSessionExists(eoSessionId, response);

        return "main";
    }

    private String ensureSessionExists(String eoSessionId, final HttpServletResponse response) {
        if (eoSessionId == null || sessionRepository.findById(eoSessionId) == null) {
            eoSessionId = createNewSession(eoSessionId, response)
                    .getId();
        }
        return eoSessionId;
    }

    private MapSession createNewSession(final String eoSessionId, final HttpServletResponse response) {
        var newSession = sessionRepository.createSession();
        sessionRepository.save(newSession);

        var sessionCookie = new Cookie(SESSION_COOKIE, newSession.getId());
        response.addCookie(sessionCookie);

        return newSession;
    }

}
