package org.swetlokognatsk.earking_out.inftrastructure;

import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.PerfectPitchConfigDependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.ports.piano.PuzzleConfigPianoKeyboardStorageAdapter;

// TODO where to put it?
public final class PuzzleConfigDependentAggregatesResolver {

    private final PuzzleConfigPianoKeyboardStorageAdapter pianoKeyboardRepository;

    public PuzzleConfigDependentAggregatesResolver(final PuzzleConfigPianoKeyboardStorageAdapter pianoKeyboardRepository) {
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    // TODO polymorphic stuff?? 
    public <DADTO extends DependentAggregatesDTO> DADTO getDependentAggregates(final Exercise exercise) {
        var pianoKeyboards = pianoKeyboardRepository.getByExercise(exercise);
        var dependentAggregates = switch (exercise) {
        case AudioPerfectPitchExercise appe -> new PerfectPitchConfigDependentAggregatesDTO(pianoKeyboards);
        case VisualPerfectPitchExercise appe -> new PerfectPitchConfigDependentAggregatesDTO(pianoKeyboards);
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };
        return (DADTO) dependentAggregates;
    }
}
