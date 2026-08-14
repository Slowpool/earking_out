package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;

public final class ExerciseService {

    private final DomainEventsFactory domainEventsFactory;
    private final EventPublisher eventPublisher;

    public ExerciseService(final DomainEventsFactory domainEventsFactory, final EventPublisher eventPublisher) {
        this.domainEventsFactory = domainEventsFactory;
        this.eventPublisher = eventPublisher;
    }

    public void pickExercise(final Exercise exercise) {
        var domainEvent = switch (exercise) {
        case AudioPerfectPitchExercise appe -> domainEventsFactory.createAudioPerfectPitchExercisePickedEvent(appe);
        default -> throw new RuntimeException("unknown exercise: " + exercise);
        };

        eventPublisher.publish(domainEvent);
    }
}
