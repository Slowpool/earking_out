package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;

public final class VisualPerfectPitchConfigAggregate extends PerfectPitchConfigAggregate<VisualPerfectPitchExercise> {
    private static final long serialVersionUID = 1L;

    public VisualPerfectPitchConfigAggregate(final VisualPerfectPitchExercise exercise, final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano, final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        super(exercise, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano, pianoKeyboardAggregates);
    }
}
