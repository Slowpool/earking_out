package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.swetlokognatsk.earking_out.core.domain.events.exercises.AudioPerfectPitchExercisePickedEvent;

public final class SpringAudioPerfectPitchExercisePickedEvent extends DomainEventWrapper<AudioPerfectPitchExercisePickedEvent> {

    public SpringAudioPerfectPitchExercisePickedEvent(final Object source, final AudioPerfectPitchExercisePickedEvent domainEvent) {
        super(source, domainEvent);
    }

}
