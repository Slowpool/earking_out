package org.swetlokognatsk.earking_out.achievements_service.core.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

// no any logic, because it's read-only for this microservice
public record UserId(@JsonValue int id) {

    public static UserId valueOf(final int id) {
        return new UserId(id);
    }
}
