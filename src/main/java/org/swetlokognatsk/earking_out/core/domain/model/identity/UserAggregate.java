package org.swetlokognatsk.earking_out.core.domain.model.identity;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import static java.util.Objects.requireNonNull;

public final class UserAggregate extends AggregateRoot<UserId> {

    private final UserUuid uuid;
    private final String name;

    public UserAggregate(final UserId id, final UserUuid uuid, final String name) {
        super(id);

        this.uuid = requireNonNull(uuid);
        this.name = requireNonNull(name);
    }

}
