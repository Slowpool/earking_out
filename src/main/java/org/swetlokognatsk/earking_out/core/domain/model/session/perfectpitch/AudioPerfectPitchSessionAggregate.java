package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;

public final class AudioPerfectPitchSessionAggregate extends PerfectPitchSessionAggregate<AudioPerfectPitchExercise, AudioPerfectPitchSolution, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO> {
    private static final long serialVersionUID = 1L;

    protected PianoKeyboardAggregate notesGuessingPianoKeyboard;

    // TODO make it read-only? HOW IT MUST BE?
    public PianoKeyboardAggregate getGuessingPianoKeyboard() {
        return notesGuessingPianoKeyboard;
    }

    // TODO think about it
    public void setGuessingPianoKeyboard(final PianoKeyboardAggregate notesGuessingPianoKeyboard) {
        this.notesGuessingPianoKeyboard = notesGuessingPianoKeyboard;
    }

    public AudioPerfectPitchSessionAggregate(final PuzzlesFactory puzzlesFactory, final SessionId id, final AudioPerfectPitchConfigDTO puzzleConfigDto, final SessionStats stats, final PianoKeyboardAggregate notesGuessingPianoKeyboard) {
        super(puzzlesFactory, id, puzzleConfigDto, stats);

        validateNotesGuessingPianoKeyboard(notesGuessingPianoKeyboard);
        this.notesGuessingPianoKeyboard = notesGuessingPianoKeyboard;
    }

    private void validateNotesGuessingPianoKeyboard(final PianoKeyboardAggregate notesGuessingPianoKeyboard) {
        Objects.requireNonNull(notesGuessingPianoKeyboard);
        // TODO add current mode logic (i.e. if it's not keyboard as piano, then make sure notesGuessingPianoKeyboard is null)
        if (notesGuessingPianoKeyboard.getId() != PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING) {
            throw new IllegalArgumentException("wrong piano keyboard received. pianoKeyboardId: " + notesGuessingPianoKeyboard.getId());
        }
    }

    // pianoKeyboardId is not passed because it's constant for this aggregate class - `PERFECT_PITCH_NOTES_GUESSING`
    public void guessViaPianoKeyPressing(final PianoKeyNumber keyNumber) {
        notesGuessingPianoKeyboard.pressKey(keyNumber);
        var solution = new AudioPerfectPitchSolution(keyNumber);
        guess(solution);
    }

    public void releasePianoKey() {
        notesGuessingPianoKeyboard.releaseKey();
    }
}
