package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.assemblers.EndAudioPerfectPitchSessionAggregateDTOAssembler;

public final class EndSessionAggregateDTOAssemblersFactory {

    public <E extends Exercise> EndSessionAggregateDTOAssembler<E, ?, ?, ?, ?, ?> create(final E exercise) {
        var endDtoAssembler = switch (exercise) {
        case AudioPerfectPitchExercise e -> new EndAudioPerfectPitchSessionAggregateDTOAssembler();
        case VisualPerfectPitchExercise e -> null;
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };
        return (EndSessionAggregateDTOAssembler<E, ?, ?, ?, ?, ?>) endDtoAssembler;
    }
}
