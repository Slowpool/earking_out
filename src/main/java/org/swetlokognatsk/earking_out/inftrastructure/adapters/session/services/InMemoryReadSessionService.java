package org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services;

import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.ports.session.services.ReadSessionService;

public final class InMemoryReadSessionService implements ReadSessionService {
    static Session<?> session;

    public Session<?> getCurrentSession() {
        return session;
    }

}
