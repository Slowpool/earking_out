package org.swetlokognatsk.earking_out.core.ports.events.messagebroker;

public interface MessageBroker {
    void send(MessageDestinations destination, Object message);
}
