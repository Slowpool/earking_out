package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;

// TODO DOMAIN IS A WRONG PLACE FOR DTOS!
public final class AudioPerfectPitchConfigDTO extends PerfectPitchConfigDTO<AudioPerfectPitchExercise> {

    // TODO reconsider passing exercise. it's always `new AudioPerfectPitchExercise()`
    public AudioPerfectPitchConfigDTO(final AudioPerfectPitchExercise exercise, final int targetNumberOfPuzzles, final boolean statsRecording, PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode) {
        super(exercise, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode);

    }
}
