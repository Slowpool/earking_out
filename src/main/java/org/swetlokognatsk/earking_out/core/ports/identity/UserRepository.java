package org.swetlokognatsk.earking_out.core.ports.identity;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.base.TypedAggregateRepository;

public interface UserRepository extends TypedAggregateRepository<UserId, UserAggregate> {
    /** the full name of user will be namePrefix + userId */
    UserAggregate createAndSaveGuestUser(String namePrefix);
}
