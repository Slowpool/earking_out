package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;

public final class AudioPerfectPitchConfigAggregate extends PerfectPitchConfigAggregate<AudioPerfectPitchExercise> {
    private static final long serialVersionUID = 1L;

    public AudioPerfectPitchConfigAggregate(final AudioPerfectPitchExercise exercise, final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        super(exercise, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, pianoKeyboardAggregates);
    }
}
