package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import java.util.function.Consumer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.context.event.GenericApplicationListenerAdapter;
import org.springframework.core.ResolvableType;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandler;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;

public final class SpringEventBus implements EventBus {

    private ConfigurableApplicationContext ctx;

    public SpringEventBus(final ConfigurableApplicationContext ctx) {
        this.ctx = ctx;
    }

    public <DE extends DomainEvent> void subscribe(final Class<DE> eventClass, final DomainEventHandler<DE> domainEventHandler) {
        ApplicationListener<?> listener;
        if (eventClass.equals(PianoKeyPressedEvent.class)) {
            listener = (SpringPianoKeyPressedEvent e) -> domainEventHandler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(NewPuzzleCreatedEvent.class)) {
            listener = (SpringNewPuzzleCreatedEvent e) -> domainEventHandler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(HintRepeatingRequestedEvent.class)) {
            listener = (SpringHintRepeatingRequestedEvent e) -> domainEventHandler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(SessionStartedEvent.class)) {
            listener = (SpringSessionStartedEvent e) -> domainEventHandler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(SessionFinishedEvent.class)) {
            listener = (SpringSessionFinishedEvent e) -> domainEventHandler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(UserTriedToGuessPuzzleEvent.class)) {
            listener = (SpringUserTriedToGuessPuzzleEvent e) -> domainEventHandler.handle((DE) (e.domainEvent));
        } else {
            throw new RuntimeException("unknown event class: " + eventClass.getName());
        }
        ctx.addApplicationListener(listener);
    }
}
