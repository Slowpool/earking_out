package org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker;

import org.swetlokognatsk.earking_out.core.ports.events.messagebroker.MessageBroker;
import org.swetlokognatsk.earking_out.core.ports.events.messagebroker.MessageDestinations;
import org.swetlokognatsk.earking_out.infrastructure.annotations.TestComponent;

@TestComponent
public class FakeMessageBroker implements MessageBroker {
    public void send(MessageDestinations destination, Object message) {

    }

}
