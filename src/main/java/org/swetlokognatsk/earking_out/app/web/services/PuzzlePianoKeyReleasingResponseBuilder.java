package org.swetlokognatsk.earking_out.app.web.services;

import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzlePianoKeyReleasingResponse;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise.unknownExercise;

@Component
public class PuzzlePianoKeyReleasingResponseBuilder {

    public <Response extends PuzzlePianoKeyReleasingResponse> Response build(final Exercise exercise) {
        var specificBuilder = switch (exercise) {
        case AudioPerfectPitchExercise appe -> DI.get(AudioPerfectPitchPianoKeyReleasingResponseBuilder.class);
        default -> throw new IllegalArgumentException(unknownExercise(exercise));
        };
        return (Response) specificBuilder.build();
    }
}
