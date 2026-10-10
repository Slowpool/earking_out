package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserUuid;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.infrastructure.annotations.TestComponent;

@TestComponent
public class TestUserResolver implements UserResolver {
    public static UserId userId = new UserId(1);
    public static UserUuid uuid = new UserUuid(java.util.UUID.fromString("00000000-0000-0000-0000-000000000001"));
    public static String name = "desktop_user";

    public UserId getCurrentUserId() {
        return userId;
    }
}
