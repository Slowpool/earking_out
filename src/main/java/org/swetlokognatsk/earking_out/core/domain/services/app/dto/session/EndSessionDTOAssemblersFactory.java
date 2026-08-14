package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.assemblers.EndAudioPerfectPitchSessionDTOAssembler;

public final class EndSessionDTOAssemblersFactory {

    public <E extends Exercise> EndSessionDTOAssembler<E, ?, ?, ?, ?, ?> create(final E exercise) {
        var endDtoAssembler = switch (exercise) {
        case AudioPerfectPitchExercise e -> new EndAudioPerfectPitchSessionDTOAssembler();
        case VisualPerfectPitchExercise e -> null;
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };
        return (EndSessionDTOAssembler<E, ?, ?, ?, ?, ?>) endDtoAssembler;
    }
}
