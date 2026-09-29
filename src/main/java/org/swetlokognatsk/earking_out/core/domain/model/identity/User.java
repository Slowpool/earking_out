package org.swetlokognatsk.earking_out.core.domain.model.identity;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

public class User extends ValueObject {

    public final UserId id;
    public final UserUuid uuid;
    public final String name;

    public User(final UserId id, final UserUuid uuid, final String name) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
    }
}
