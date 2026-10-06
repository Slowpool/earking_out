package org.swetlokognatsk.earking_out.core.domain.model.identity;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

@Service
public class UserAggregatesFactory extends AggregatesFactory<UserAggregate> {

    public UserAggregatesFactory(final ObjectCloner cloner) {
        super(cloner);
    }

    public UserAggregate create(final UserId userId, final UserUuid uuid, final String name) {
        return new UserAggregate(userId, uuid, name);
    }
}
