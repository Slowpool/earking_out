package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserUuid;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.infrastructure.annotations.TestComponent;
import lombok.Getter;

@TestComponent
public class TestUserResolver implements UserResolver {
    private UserId userId = new UserId(1);
    @Getter
    private UserUuid uuid = new UserUuid(java.util.UUID.fromString("00000000-0000-0000-0000-000000000001"));
    @Getter
    private String name = "desktop_user";

    public UserId getCurrentUserId() {
        return userId;
    }

    public void setCurrentUserId(final UserId userId) {
        this.userId = userId;
    }
}
