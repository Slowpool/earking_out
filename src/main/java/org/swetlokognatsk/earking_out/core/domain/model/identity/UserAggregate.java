package org.swetlokognatsk.earking_out.core.domain.model.identity;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;

public final class UserAggregate extends AggregateRoot<UserId> {

    private final UserUuid uuid;

    public UserAggregate(final UserId id, final UserUuid uuid) {
        super(id);

        this.uuid = uuid;
    }

}
