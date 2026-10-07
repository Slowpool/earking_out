package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.app.web.SessionAttributes.USER_ID;

@WebComponent
@AllArgsConstructor
public class SpringSessionUserResolver implements UserResolver {

    private final HttpSession session;

    public UserId getCurrentUserId() {
        return (UserId) session.getAttribute(USER_ID);
    }

}
