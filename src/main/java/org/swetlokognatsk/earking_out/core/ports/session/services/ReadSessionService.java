package org.swetlokognatsk.earking_out.core.ports.session.services;

import org.swetlokognatsk.earking_out.core.domain.model.session.Session;

public interface ReadSessionService {
    Session<?> getCurrentSession();
}
