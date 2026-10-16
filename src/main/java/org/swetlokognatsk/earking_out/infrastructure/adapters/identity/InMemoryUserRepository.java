package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.infrastructure.annotations.TestRepository;

@TestRepository
public class InMemoryUserRepository implements UserRepository {

    public UserAggregate createAndSaveGuestUser(final String namePrefix) {
        return null;
    }

    public UserAggregate get(final UserId id) {
        return null;
    }

    public void save(final UserAggregate userAggregate) {

    }
}
