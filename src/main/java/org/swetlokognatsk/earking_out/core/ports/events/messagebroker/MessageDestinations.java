package org.swetlokognatsk.earking_out.core.ports.events.messagebroker;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MessageDestinations {
    DOMAIN_EVENTS("domain-events");

    final String destination;
}
