package org.swetlokognatsk.earking_out.app.web.services.responsebuilders;

import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.app.web.models.responses.PuzzlePianoKeyPressingResponse;
import org.swetlokognatsk.earking_out.app.web.services.responsebuilders.perfectpitch.AudioPerfectPitchPianoKeyPressingResponseBuilder;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise.unknownExercise;

@Component
public class PuzzlePianoKeyPressingResponseBuilder {

    public <R extends PuzzlePianoKeyPressingResponse> R build(final Exercise exercise, final SessionId sessionId) {
        var specificBuilder = switch (exercise) {
        // TODO create interface for other builders
        case AudioPerfectPitchExercise appe -> DI.get(AudioPerfectPitchPianoKeyPressingResponseBuilder.class);
        default -> throw new IllegalArgumentException(unknownExercise(exercise));
        };
        return (R) specificBuilder.build(sessionId);
    }
}
