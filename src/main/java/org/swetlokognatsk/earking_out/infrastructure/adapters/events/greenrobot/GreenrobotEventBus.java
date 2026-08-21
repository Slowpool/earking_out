package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.greenrobot.eventbus.EventBus;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintDemonstratingOnHintRepeatingRequestedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintDemonstratingOnNewPuzzleCreatedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.AudioPerfectPitchGuessingOnPianoKeyPressedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.SessionPianoKeyboardUpdatingOnSessionStartedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;

// TODO learning tests
public final class GreenrobotEventBus implements org.swetlokognatsk.earking_out.core.ports.events.EventBus, EventPublisher {
    private final EventBus innerEventBus;

    // TODO should it be injected at all?
    public GreenrobotEventBus(final EventBus eventBus) {
        this.innerEventBus = eventBus;
    }

    public <DE extends DomainEvent> void subscribe(final Class<DE> eventClass, final DomainEventHandler<DE> domainEventHandler) {
        // TODO remove try catch (now it's only for tests. some event handlers are not created for greenrobot implementation yet)
        try {
            var handler = wrapDomainEventHandler(domainEventHandler);
            innerEventBus.register(handler);
        }
        catch (IllegalArgumentException e) {
        }
    }

    private Object wrapDomainEventHandler(final DomainEventHandler<?> domainEventHandler) {
        return switch (domainEventHandler) {
        case HintDemonstratingOnNewPuzzleCreatedHandler dh -> new GreenrobotHintDemonstratingOnNewPuzzleCreatedHandler(dh);
        case HintDemonstratingOnHintRepeatingRequestedHandler dh -> new GreenrobotHintDemonstratingOnHintRepeatingRequestedHandler(dh);
        case SessionPianoKeyboardUpdatingOnSessionStartedHandler dh -> new GreenrobotSessionPianoKeyboardUpdatingOnSessionStartedHandler(dh);
        case AudioPerfectPitchGuessingOnPianoKeyPressedHandler dh -> new GreenrobotSessionGuessingOnPianoKeyPressedHandler(dh);
        default -> throw new IllegalArgumentException("unkown domain event handler: " + domainEventHandler.getClass().getName());
        };
    }

    public void publish(final DomainEvent event) {
        innerEventBus.post(event);
    }
}
