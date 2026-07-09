package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import java.io.Serializable;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;

public final class AudioPerfectPitchSessionAggregate extends PerfectPitchSessionAggregate<AudioPerfectPitchExercise, AudioPerfectPitchSolution, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO> implements Serializable {
    protected PianoKeyboardAggregate notesGuessingPianoKeyboard;

    public PianoKeyboardAggregate getGuessingPianoKeyboard() {
        // TODO make it read-only
        return notesGuessingPianoKeyboard;
    }

    // TODO think about it
    public void setGuessingPianoKeyboard(final PianoKeyboardAggregate notesGuessingPianoKeyboard) {
        this.notesGuessingPianoKeyboard = notesGuessingPianoKeyboard;
    }

    public AudioPerfectPitchSessionAggregate(final SessionId id, final AudioPerfectPitchConfigDTO puzzleConfigDto, final SessionStats stats, final PianoKeyboardAggregate notesGuessingPianoKeyboard) {
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
    // TODO is it fine to have such an aggregate command that calls another command (guess)?
    public void guessViaPianoKeyPressing(final PianoKeyNumber keyNumber) {
        notesGuessingPianoKeyboard.pressKey(keyNumber);
        var solution = new AudioPerfectPitchSolution(keyNumber);
        guess(solution);
    }

    public void releasePianoKey() {
        notesGuessingPianoKeyboard.releaseKey();
    }

    public void hearAgain() {
        demonstrateHint();
    }
}
