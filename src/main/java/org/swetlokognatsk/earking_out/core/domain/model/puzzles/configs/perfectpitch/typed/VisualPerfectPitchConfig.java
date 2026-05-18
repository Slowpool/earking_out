package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;

public class VisualPerfectPitchConfig extends PerfectPitchConfig<VisualPerfectPitchExercise> {

    public VisualPerfectPitchConfig(final int targetNumberOfPuzzles, final boolean statsRecording, final NoteWithAccidental[] notesForPuzzle, final NoteWithAccidental rootNote, final PerfectPitchInputMode inputMode) {
        super(targetNumberOfPuzzles, statsRecording, notesForPuzzle, rootNote, inputMode);

    }

    protected final ExerciseTypes getExerciseType() {
        return ExerciseTypes.VISUAL;
    }
}
