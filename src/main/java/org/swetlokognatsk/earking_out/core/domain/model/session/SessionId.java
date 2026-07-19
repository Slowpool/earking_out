package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/** Value object */
public record SessionId(UUID id) implements Serializable {
    private static final long serialVersionUID = 1L;

    public SessionId {
        Objects.requireNonNull(id);
    }

    public static SessionId random() {
        return new SessionId(UUID.randomUUID());
    }
}
