package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import java.util.concurrent.Executor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
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

public final class SpringEventBus implements EventBus {

    private final ConfigurableApplicationContext ctx;
    private final Executor taskExecutor;

    public SpringEventBus(final ConfigurableApplicationContext ctx, final Executor taskExecutor) {
        this.ctx = ctx;
        this.taskExecutor = taskExecutor;
    }

    public <DE extends DomainEvent> void subscribe(final Class<DE> eventClass, DomainEventHandler<DE> domainEventHandler) {
        var handler = shouldWrapInTask(domainEventHandler)
                ? new DomainEventHandler<DE>() {
                    public void handle(final DE domainEvent) {
                        taskExecutor.execute(() -> {
                            domainEventHandler.handle(domainEvent);
                        });
                    }
                }
                : domainEventHandler;

        ApplicationListener<?> listener;
        if (eventClass.equals(PianoKeyPressedEvent.class)) {
            listener = (SpringPianoKeyPressedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(NewPuzzleCreatedEvent.class)) {
            listener = (SpringNewPuzzleCreatedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(HintRepeatingRequestedEvent.class)) {
            listener = (SpringHintRepeatingRequestedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(SessionStartedEvent.class)) {
            listener = (SpringSessionStartedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(SessionFinishedEvent.class)) {
            listener = (SpringSessionFinishedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(UserTriedToGuessPuzzleEvent.class)) {
            listener = (SpringUserTriedToGuessPuzzleEvent e) -> handler.handle((DE) (e.domainEvent));
        } else if (eventClass.equals(AudioPerfectPitchExercisePickedEvent.class)) {
            listener = (SpringAudioPerfectPitchExercisePickedEvent e) -> handler.handle((DE) (e.domainEvent));
        } else {
            throw new RuntimeException("unknown event class: " + eventClass.getName());
        }
        ctx.addApplicationListener(listener);
    }

    private final boolean shouldWrapInTask(final DomainEventHandler<?> domainEventHandler) {
        // smart hack
        return domainEventHandler.getClass()
                .getSimpleName()
                .startsWith("Log");
    }
}
