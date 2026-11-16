package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events;

import java.time.LocalDateTime;
import java.util.Map;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;

// TODO test properties logic
/**
 * simplified common class for all domain events, because they are read-only and
 * there's no need in any validation logic.
 */
public record DomainEvent(String type, UserId userId, LocalDateTime timestamp, Map<String, Object> properties) {

    /**
     * @param propertyPath e.g. "user", "user.name", "user.name.first"
     * @return
     */
    public Object get(final String propertyPath) {
        Object node = properties;
        Map<String, Object> n;
        for (var property : propertyPath.split("\\.")) {
            try {
                n = (Map<String, Object>) node;
                if (!n.containsKey(property)) {
                    throw new IllegalArgumentException();
                }
                node = n.get(property);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException();
            }
        }
        return node;
    }

    public <T> T get(final String property, final Class<T> propertyClass) {
        return (T) properties.get(property);
    }
}
