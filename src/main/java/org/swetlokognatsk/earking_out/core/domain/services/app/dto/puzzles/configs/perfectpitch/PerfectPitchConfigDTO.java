package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public abstract class PerfectPitchConfigDTO<E extends PerfectPitchExercise> extends PuzzleConfigDTO<E> {
    public final PianoKeyNumber[] normalizedNotesForPuzzle;
    public final PianoKeyNumber normalizedRootNote;
    public final PerfectPitchInputMode inputMode;
    public final boolean soundlessGuessingPiano;

    public PerfectPitchConfigDTO(final E exercise, final int targetNumberOfPuzzles, final boolean statsRecording, PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano) {
        super(exercise, targetNumberOfPuzzles, statsRecording);
        this.normalizedNotesForPuzzle = normalizedNotesForPuzzle;
        this.normalizedRootNote = normalizedRootNote;
        this.inputMode = inputMode;
        this.soundlessGuessingPiano = soundlessGuessingPiano;
    }

}
