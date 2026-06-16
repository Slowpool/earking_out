package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;

public final class VisualPerfectPitchConfigAggregate extends PerfectPitchConfigAggregate<VisualPerfectPitchExercise> {

    public VisualPerfectPitchConfigAggregate(final VisualPerfectPitchExercise exercise, final int targetNumberOfPuzzles, final boolean statsRecording, final byte[] normalizedNotesForPuzzle, final Byte normalizedRootNote, final PerfectPitchInputMode inputMode, final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        super(exercise, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, pianoKeyboardAggregates);

    }
}
