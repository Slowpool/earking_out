package org.swetlokognatsk.earking_out.core.domain.model.identity;

import static java.util.Objects.*;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

public class User extends ValueObject {

    public final UserId id;
    public final UserUuid uuid;
    public final String name;

    public User(final UserId id, final UserUuid uuid, final String name) {
        this.id = requireNonNull(id);
        this.uuid = requireNonNull(uuid);
        this.name = requireNonNull(name);
    }
}
