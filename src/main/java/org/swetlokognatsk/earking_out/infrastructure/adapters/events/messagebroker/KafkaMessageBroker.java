package org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker;

import org.swetlokognatsk.earking_out.core.ports.events.messagebroker.MessageBroker;
import org.swetlokognatsk.earking_out.core.ports.events.messagebroker.MessageDestinations;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;

@WebComponent
public class KafkaMessageBroker implements MessageBroker {

    public void send(final MessageDestinations destination, final Object message) {
        System.out.println("");
        System.out.println("sending the message to kafka: " + message.toString());
        System.out.println("");
    }

}
