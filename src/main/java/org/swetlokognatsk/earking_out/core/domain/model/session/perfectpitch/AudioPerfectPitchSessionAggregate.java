package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;

public final class AudioPerfectPitchSessionAggregate extends PerfectPitchSessionAggregate<AudioPerfectPitchExercise, AudioPerfectPitchSolution, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO> {
    private static final long serialVersionUID = 1L;

    public AudioPerfectPitchSessionAggregate(final PuzzlesFactory puzzlesFactory, final SessionId id, final AudioPerfectPitchConfigDTO puzzleConfigDto, final SessionStats stats) {
        super(puzzlesFactory, id, puzzleConfigDto, stats);
    }

    // pianoKeyboardId is not passed because it's constant for this aggregate class - `PERFECT_PITCH_NOTES_GUESSING`
    public void guessViaPianoKeyPressing(final PianoKeyNumber keyNumber) {
        var solution = new AudioPerfectPitchSolution(keyNumber);
        guess(solution);
    }
}
