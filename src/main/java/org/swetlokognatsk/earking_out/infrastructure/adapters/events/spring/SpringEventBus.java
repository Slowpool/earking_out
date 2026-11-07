package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.exercises.AudioPerfectPitchExercisePickedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandler;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.infrastructure.AsyncTaskExecutorByUser;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.EventBuses;

@Component
@ConditionalOnProperty(name = "app.eventbus", havingValue = EventBuses.SPRING)
public final class SpringEventBus implements EventBus {

    private final ConfigurableApplicationContext ctx;
    private final AsyncTaskExecutorByUser asyncTaskExecutorByUser;
    private final UserResolver userResolver;

    public SpringEventBus(final ConfigurableApplicationContext ctx, final AsyncTaskExecutorByUser asyncTaskExecutorByUser, final UserResolver userResolver) {
        this.ctx = ctx;
        this.asyncTaskExecutorByUser = asyncTaskExecutorByUser;
        this.userResolver = userResolver;
    }

    public <DE extends DomainEvent> void subscribe(final Class<DE> eventClass, DomainEventHandler<DE> domainEventHandler) {
        // TODO one more optimization: keep keep keep keep flush keep keep keep flush
        var handler = shouldBeAsync(domainEventHandler)
                ? asyncTaskExecutorByUser.wrapInAsync(domainEventHandler)
                : domainEventHandler;

        ApplicationListener<?> l;
        if (eventClass.equals(PianoKeyPressedEvent.class)) {
            l = (SpringPianoKeyPressedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(NewPuzzleCreatedEvent.class)) {
            l = (SpringNewPuzzleCreatedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(HintRepeatingRequestedEvent.class)) {
            l = (SpringHintRepeatingRequestedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(SessionStartedEvent.class)) {
            l = (SpringSessionStartedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(SessionFinishedEvent.class)) {
            l = (SpringSessionFinishedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(UserTriedToGuessPuzzleEvent.class)) {
            l = (SpringUserTriedToGuessPuzzleEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(AudioPerfectPitchExercisePickedEvent.class)) {
            l = (SpringAudioPerfectPitchExercisePickedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else {
            throw new RuntimeException("unknown event class: " + eventClass.getName());
        }
        ctx.addApplicationListener(l);
    }

    private final boolean shouldBeAsync(final DomainEventHandler<?> domainEventHandler) {
        // smart hack
        return domainEventHandler.getClass()
                .getSimpleName()
                .startsWith("Log");
    }
}
