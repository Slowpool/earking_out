package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class AudioPerfectPitchConfigAggregate extends PerfectPitchConfigAggregate<AudioPerfectPitchExercise> {

    public AudioPerfectPitchConfigAggregate(final AudioPerfectPitchExercise exercise, final int targetNumberOfPuzzles, final boolean statsRecording, final byte[] normalizedNotesForPuzzle, final Byte normalizedRootNote, final PerfectPitchInputMode inputMode, final PianoKeyboardRepository pianoKeyboardRepository) {
        super(exercise, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, pianoKeyboardRepository);

    }
}
