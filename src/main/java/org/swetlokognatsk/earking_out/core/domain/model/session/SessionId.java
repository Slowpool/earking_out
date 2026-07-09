package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.io.Serializable;
import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

public final class SessionId extends ValueObject implements Serializable {
    public final UUID id;

    public SessionId(final UUID id) {
        this.id = id;
    }

    public static SessionId random() {
        return new SessionId(UUID.randomUUID());
    }

    public int hashCode() {
        return id.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SessionId)) {
            return false;
        }
        var other = (SessionId) obj;
        return id.equals(other.id);
    }
}
