package org.swetlokognatsk.earking_out.core.domain.events.exercises;

import java.time.LocalDateTime;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;

public final class AudioPerfectPitchExercisePickedEvent extends ExercisePickedEvent<AudioPerfectPitchExercise> {
    private static final long serialVersionUID = 1L;

    public AudioPerfectPitchExercisePickedEvent(final LocalDateTime timestamp, final AudioPerfectPitchExercise exercise) {
        super(timestamp, exercise);
    }

}
