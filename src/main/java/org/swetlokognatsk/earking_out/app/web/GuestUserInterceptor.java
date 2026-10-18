package org.swetlokognatsk.earking_out.app.web;

import org.springframework.web.servlet.HandlerInterceptor;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.services.app.identity.UserService;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.app.web.SessionAttributes.USER_ID;

@WebComponent
@AllArgsConstructor
public class GuestUserInterceptor implements HandlerInterceptor {

    private final UserService userService;
    private final UserRepository userRepository;

    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) {
        var session = request.getSession();
        ensureUserExists(session);
        return true;
    }

    private void ensureUserExists(final HttpSession session) {
        var userId = (UserId) session.getAttribute(USER_ID);
        if (userId != null && !userExists(userId)) {
            userId = null;
        }

        if (userId == null) {
            createAndAttachGuestUser(session);
        }
    }

    private boolean userExists(final UserId userId) {
        boolean result;
        try {
            userRepository.get(userId);
            result = true;
        } catch (AggregateNotFoundException e) {
            result = false;
        }
        return result;
    }

    private void createAndAttachGuestUser(final HttpSession session) {
        var userId = userService.createGuestUser();
        session.setAttribute(USER_ID, userId);
    }
}
