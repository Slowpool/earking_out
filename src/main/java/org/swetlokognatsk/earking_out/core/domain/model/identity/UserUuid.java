package org.swetlokognatsk.earking_out.core.domain.model.identity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record UserUuid(UUID uuid) implements Serializable {
    private static final long serialVersionUID = 1L;

    public UserUuid {
        Objects.requireNonNull(uuid);
    }

    public static UserUuid random() {
        return new UserUuid(UUID.randomUUID());
    }

    public String toString() {
        return uuid.toString();
    }
}
