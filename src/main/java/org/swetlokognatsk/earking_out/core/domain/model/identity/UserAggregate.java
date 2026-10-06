package org.swetlokognatsk.earking_out.core.domain.model.identity;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;

public final class UserAggregate extends AggregateRoot<UserId> {

    private final UserUuid uuid;
    private final String name;

    public UserAggregate(final UserId id, final UserUuid uuid, final String name) {
        super(id);

        this.uuid = uuid;
        this.name = name;
    }

}
