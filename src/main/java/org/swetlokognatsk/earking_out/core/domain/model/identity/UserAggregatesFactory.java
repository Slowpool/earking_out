package org.swetlokognatsk.earking_out.core.domain.model.identity;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

public class UserAggregatesFactory extends AggregatesFactory<UserAggregate> {

    public UserAggregatesFactory(final ObjectCloner cloner) {
        super(cloner);
    }

    public UserAggregate create(final UserId userId, final UserUuid uuid, final String name) {
        return new UserAggregate(userId, uuid, name);
    }
}
