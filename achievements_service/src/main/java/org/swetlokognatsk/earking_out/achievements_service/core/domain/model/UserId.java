package org.swetlokognatsk.earking_out.achievements_service.core.domain.model;

// no any logic, because it's read-only for this microservice
public record UserId(int id) {

    public static UserId valueOf(final int id) {
        return new UserId(id);
    }
}
