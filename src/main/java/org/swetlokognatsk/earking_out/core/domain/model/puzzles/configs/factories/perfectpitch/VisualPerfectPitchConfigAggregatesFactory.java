package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class VisualPerfectPitchConfigAggregatesFactory extends PuzzleConfigAggregatesFactory<VisualPerfectPitchConfigAggregate> {

    public VisualPerfectPitchConfigAggregate createDefault() {
        return new VisualPerfectPitchConfigAggregate(0, true, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO, getPianoKeyboardRepository());
    }

    // TODO exterminate DRY violation (this is copied from AudioPerfectPitch...)
    protected static PianoKeyboardRepository getPianoKeyboardRepository() {
        return DI.get(PianoKeyboardRepository.class);
    }

    public static getShallowCopy() {
        var puzzleConfig = switch (exercise) {
        case VisualPerfectPitchExercise e -> {
            var puzzleConfigAggregateSource = ()
            var puzzleConfigAggregateCopy = VisualPerfectPitchConfigAggregatesFactory.create(vppc.targetNumberOfPuzzles, vppc.statsRecording, vppc.normalizedNotesForPuzzle, vppc.normalizedRootNote, vppc.inputMode);
            yield aggregate;
        };
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchConfigAggregate(appc.targetNumberOfPuzzles, appc.statsRecording, appc.normalizedNotesForPuzzle, appc.normalizedRootNote, appc.inputMode);
        default -> throw new RuntimeException("unknown exercise on config fetching: " + exercise);
        };
    }
}
