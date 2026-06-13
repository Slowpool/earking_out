package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public abstract class PerfectPitchConfigDTO<E extends PerfectPitchExercise> extends PuzzleConfigDTO<E> {
    public final byte[] normalizedNotesForPuzzle;
    public final Byte normalizedRootNote;
    public final PerfectPitchInputMode inputMode;

    public PerfectPitchConfigDTO(final E exercise, final int targetNumberOfPuzzles, final boolean statsRecording, byte[] normalizedNotesForPuzzle, final Byte normalizedRootNote, final PerfectPitchInputMode inputMode) {
        super(exercise, targetNumberOfPuzzles, statsRecording);
        this.normalizedNotesForPuzzle = normalizedNotesForPuzzle;
        this.normalizedRootNote = normalizedRootNote;
        this.inputMode = inputMode;
    }

}
