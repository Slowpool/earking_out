package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;

public final class SpringEventPublisher implements EventPublisher {
    private ApplicationEventPublisher eventPublisher;

    // TODO what will you do with compiler warning
    public SpringEventPublisher(final ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(final DomainEvent event) {
        var wrappedEvent = wrapEvent(event);
        eventPublisher.publishEvent(wrappedEvent);
    }

    private ApplicationEvent wrapEvent(final DomainEvent domainEvent) {
        return switch (domainEvent) {
            // TODO what to do with source? remain as is? remove domainEvent?
        case PianoKeyPressedEvent de -> new SpringPianoKeyPressedEvent(de, de);
        case NewPuzzleCreatedEvent de -> new SpringNewPuzzleCreatedEvent(de, de);
        case HintRepeatingRequestedEvent de -> new SpringHintRepeatingRequestedEvent(de, de);
        case SessionStartedEvent de -> new SpringSessionStartedEvent(de, de);
        case SessionFinishedEvent de -> new SpringSessionFinishedEvent(de, de);
        case UserTriedToGuessPuzzleEvent de -> new SpringUserTriedToGuessPuzzleEvent(de, de);
        default -> throw new RuntimeException("unknown domainEvent: " + domainEvent.getClass().getName());
        };
    }
}
