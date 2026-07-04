package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import java.util.Map;
import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import static org.swetlokognatsk.earking_out.core.domain.helpers.PianoKeyboardHelper.*;

public final class AudioPerfectPitchSessionAggregate extends PerfectPitchSessionAggregate<AudioPerfectPitchExercise, AudioPerfectPitchSolution, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO> {
    protected final PianoKeyboardAggregate notesGuessingPianoKeyboard;

    public AudioPerfectPitchSessionAggregate(final UUID id, final AudioPerfectPitchConfigDTO puzzleConfigDto, final SessionStats stats, final PianoKeyboardAggregate notesGuessingPianoKeyboard) {
        super(id, puzzleConfigDto, stats);

        this.notesGuessingPianoKeyboard = notesGuessingPianoKeyboard;
        validateNotesGuessingPianoKeyboard(notesGuessingPianoKeyboard);
    }

    protected void validateNotesGuessingPianoKeyboard(final PianoKeyboardAggregate notesGuessingPianoKeyboard) {
        // TODO add current mode logic (i.e. if it's not keyboard as piano, then make sure notesGuessingPianoKeyboard is null)
        if (notesGuessingPianoKeyboard.getId() != PianoKeyboardId.PERFECT_PITCH_NOTES_GUESSING) {
            throw new IllegalArgumentException("wrong piano keyboard received. pianoKeyboardId: " + notesGuessingPianoKeyboard.getId());
        }
    }

    // pianoKeyboardId is not passed because it's constant for this aggregate class - `PERFECT_PITCH_NOTES_GUESSING`
    public void guessViaPianoKeyPressing(final PianoKeyNumber keyNumber) {
        // pianoKeyboardAggregates
    }

    public void releasePianoKey() {

    }

}
