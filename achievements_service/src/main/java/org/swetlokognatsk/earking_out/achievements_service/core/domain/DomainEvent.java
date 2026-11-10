package org.swetlokognatsk.earking_out.achievements_service.core.domain;

import java.time.LocalDateTime;
import java.util.Map;

public record DomainEvent(String type, int userId, LocalDateTime timestamp, Map<String, Object> properties) {
}
