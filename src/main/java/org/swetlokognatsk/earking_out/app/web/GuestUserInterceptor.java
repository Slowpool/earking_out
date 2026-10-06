package org.swetlokognatsk.earking_out.app.web;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.services.app.identity.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GuestUserInterceptor implements HandlerInterceptor {

    public static final String USER_ID = "USER_ID";

    private final UserService userService;

    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) {
        var session = request.getSession();
        ensureUserIdExists(session);
        return true;
    }

    private void ensureUserIdExists(final HttpSession session) {
        var userId = (UserId) session.getAttribute(USER_ID);
        if (userId == null) {
            createAndAttachGuestUser(session);
        }
    }

    private void createAndAttachGuestUser(final HttpSession session) {
        var userId = userService.createGuestUser();
        session.setAttribute(USER_ID, userId);
        // TODO sessionRepository.save(session)?
    }
}
