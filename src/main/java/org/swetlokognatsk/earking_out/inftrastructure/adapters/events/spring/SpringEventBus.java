package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import org.swetlokognatsk.earking_out.core.ports.events.EventType;

public final class SpringEventBus implements EventBus {
    
    public <DE extends DomainEvent> void subscribe(final EventType<DE> eventType, Consumer<DE> action) {
        // no implementation. subscription happens via listeners' annotations
    }

}
