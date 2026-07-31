package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.greenrobot;

import java.util.function.Consumer;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;
import org.swetlokognatsk.earking_out.core.ports.events.EventType;

public final class GreenrobotEventBus implements org.swetlokognatsk.earking_out.core.ports.events.EventBus, EventPublisher {
    private final EventBus innerEventBus;

    // TODO should it be injected at all?
    public GreenrobotEventBus(final EventBus eventBus) {
        this.innerEventBus = eventBus;
    }

    public <DE extends DomainEvent> void subscribe(final EventType<DE> et, Consumer<DE> action) {
        innerEventBus.register(new Object() {
            @Subscribe
            public void handleEvent(final DE event) {
                action.accept(event);
            }
        });
    }

    public void publish(final DomainEvent event) {
        innerEventBus.post(event);
    }
}
