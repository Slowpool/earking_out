package org.swetlokognatsk.earking_out.app.web.controllers;

import static org.swetlokognatsk.earking_out.app.web.SessionAttributes.*;
import static org.springframework.http.ResponseEntity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/identity")
@AllArgsConstructor
public class IdentityController {

    private final UserResolver userResolver;
    private final UserRepository userRepository;

    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut(final HttpSession session, final HttpServletResponse response) {
        var userId = userResolver.getCurrentUserId();
        var user = userRepository.get(userId);
        if (user.isGuest()) {
            return status(401)
                    .body("guest cannot sign out");
        }

        forgetCurrentUser(session);

        response.setHeader("HX-Refresh", "true");
        return ok(null);
    }

    private void forgetCurrentUser(final HttpSession session) {
        session.removeAttribute(USER_ID);
        session.removeAttribute(LOCALE);
    }
}
