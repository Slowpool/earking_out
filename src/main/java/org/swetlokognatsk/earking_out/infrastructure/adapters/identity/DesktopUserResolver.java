package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserUuid;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.infrastructure.annotations.DesktopComponent;

@DesktopComponent
public class DesktopUserResolver implements UserResolver {
    private static final UserId USER_ID = new UserId(1);
    private static final UserUuid UUID = new UserUuid(java.util.UUID.fromString("00000000-0000-0000-0000-000000000001"));
    private static final String NAME = "desktop_user";

    public UserId getCurrentUserId() {
        return USER_ID;
    }

}
