package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;

public class AudioPerfectPitchConfig extends PerfectPitchConfig<AudioPerfectPitchExercise> {

    public AudioPerfectPitchConfig(final int targetNumberOfPuzzles, final boolean statsRecording, final NoteWithAccidental[] notesForPuzzle, final NoteWithAccidental rootNote, final PerfectPitchInputMode inputMode) {
        super(targetNumberOfPuzzles, statsRecording, notesForPuzzle, rootNote, inputMode);

    }

    protected final ExerciseTypes getExerciseType() {
        return ExerciseTypes.AUDIO;
    }
}
