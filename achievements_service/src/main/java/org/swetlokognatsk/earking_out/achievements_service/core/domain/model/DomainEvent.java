package org.swetlokognatsk.earking_out.achievements_service.core.domain.model;

import java.time.LocalDateTime;
import java.util.Map;

// TODO test properties logic
/**
 * simplified common class for all domain events, because they are read-only and
 * there's no need in any validation logic.
 */
public record DomainEvent(String type, UserId userId, LocalDateTime timestamp, Map<String, Object> properties) {

    public Object get(final String property) {
        return get(property, Object.class);
    }

    public <T> T get(final String property, final Class<T> propertyClass) {
        return (T) properties.get(property);
    }
}
