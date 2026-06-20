package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTOAssembler;

public final class EndPuzzleConfigDTOAssemblersFactory {

    private EndPuzzleConfigDTOAssemblersFactory() {
    }

    public static <E extends Exercise, EDTOA extends EndPuzzleConfigDTOAssembler<E, ?, ?>> EDTOA create(E exercise) {
        var endAssembler = switch (exercise) {
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchConfigDTOAssembler();
        case VisualPerfectPitchExercise e -> new VisualPerfectPitchConfigDTOAssembler();
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };
        return (EDTOA) endAssembler;
    }
}
