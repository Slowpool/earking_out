package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;

public final class AudioPerfectPitchConfig extends PerfectPitchConfig<AudioPerfectPitchExercise> {

    public AudioPerfectPitchConfig(final int targetNumberOfPuzzles, final boolean statsRecording, final byte[] normalizedNotesForPuzzle, final Byte normalizedRootNote, final PerfectPitchInputMode inputMode) {
        super(targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode);

    }

    protected final ExerciseTypes getExerciseType() {
        return ExerciseTypes.AUDIO;
    }
}
