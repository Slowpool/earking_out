package org.swetlokognatsk.earking_out.core.ports.identity;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;

public interface UserResolver {
    UserId getCurrentUserId();
}
