package org.swetlokognatsk.earking_out.core.domain.model.identity;

public class DesktopUser extends User {
    private static final UserId USER_ID = new UserId(1);
    private static final UserUuid UUID = new UserUuid(java.util.UUID.fromString("00000000-0000-0000-0000-000000000001"));
    private static final String NAME = "desktop_user";

    public DesktopUser() {
        super(USER_ID, UUID, NAME);
    }
}
