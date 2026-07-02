package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;

public final class AudioPerfectPitchSessionAggregate extends PerfectPitchSessionAggregate<AudioPerfectPitchExercise, AudioPerfectPitchSolution, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO> {

    public AudioPerfectPitchSessionAggregate(final UUID id, final AudioPerfectPitchConfigDTO puzzleConfigDto, final SessionStats stats) {
        super(id, puzzleConfigDto, stats);
    }

    // pianoKeyboardId is not passed because it's constant for this aggregate class - `PERFECT_PITCH_NOTES_GUESSING`
    public void guessViaPianoKeyPressing(final PianoKeyNumber keyNumber) {

    }

    public void releasePianoKey() {

    }

}
