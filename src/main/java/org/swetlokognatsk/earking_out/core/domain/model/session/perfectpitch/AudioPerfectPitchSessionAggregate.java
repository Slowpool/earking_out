package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import java.io.IOException;
import java.io.ObjectInputStream;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.InvalidTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.OutOfRangeTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesParsingService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public final class AudioPerfectPitchSessionAggregate extends PerfectPitchSessionAggregate<AudioPerfectPitchExercise, AudioPerfectPitchSolution, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO> {
    private static final long serialVersionUID = 1L;

    private transient NotesNormalizingService notesNormalizer;
    private transient NotesParsingService notesParser;

    public AudioPerfectPitchSessionAggregate(final PuzzlesFactory puzzlesFactory, final SessionId id, final AudioPerfectPitchConfigDTO puzzleConfigDto, final SessionStats stats, final NotesParsingService notesParser, final NotesNormalizingService notesNormalizer) {
        super(puzzlesFactory, id, puzzleConfigDto, stats);
        this.notesNormalizer = notesNormalizer;
        this.notesParser = notesParser;
    }

    // pianoKeyboardId is not passed because it's constant for this aggregate class - `PERFECT_PITCH_NOTES_GUESSING`
    public void guessViaPianoKeyPressing(final PianoKeyNumber pianoKeyNumber) {
        guess(pianoKeyNumber);
    }

    public void guessViaTextNote(final String textNote) throws InvalidTextNoteException, OutOfRangeTextNoteException {
        var note = notesParser.parse(textNote);
        var pianoKeyNumber = notesNormalizer.normalize(note);
        guess(pianoKeyNumber);
    }

    private void guess(final PianoKeyNumber keyNumber) {
        var solution = new AudioPerfectPitchSolution(keyNumber);
        guess(solution);
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        // TODO weeeelll, actually it's a bad idea. if it's test, then after serialization it'll pull real implementations instead of during-the-test-created mocks
        notesNormalizer = DI.get(NotesNormalizingService.class);
        notesParser = DI.get(NotesParsingService.class);
    }
}
