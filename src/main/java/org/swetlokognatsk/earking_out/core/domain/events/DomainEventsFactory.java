package org.swetlokognatsk.earking_out.core.domain.events;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.exercises.AudioPerfectPitchExercisePickedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionDTO;

public final class DomainEventsFactory {

    private LocalDateTime createTimestamp() {
        return LocalDateTime.now();
    }

    public PianoKeyPressedEvent createPianoKeyPressedEvent(final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber pianoKeyNumber) {
        var timestamp = createTimestamp();
        return new PianoKeyPressedEvent(timestamp, pianoKeyboardId, pianoKeyNumber);
    }

    public <E extends Exercise> NewPuzzleCreatedEvent createNewPuzzleCreatedEvent(SessionDTO<E, ?, ?, ?> sessionDto, final Puzzle<E, ?> puzzle) {
        var timestamp = createTimestamp();
        return new NewPuzzleCreatedEvent(timestamp, sessionDto, puzzle);
    }

    public <E extends Exercise> HintRepeatingRequestedEvent createHintRepeatingRequestedEvent(final SessionDTO<E, ?, ?, ?> sessionDto, final Puzzle<E, ?> puzzle) {
        var timestamp = createTimestamp();
        return new HintRepeatingRequestedEvent(timestamp, sessionDto, puzzle);
    }

    public UserTriedToGuessPuzzleEvent createUserTriedToGuessPuzzleEvent(final SessionDTO<?, ?, ?, ?> sessionDto, final int puzzleNumber, final Solution guess, final int attempt, final boolean success) {
        var timestamp = createTimestamp();
        return new UserTriedToGuessPuzzleEvent(timestamp, sessionDto, puzzleNumber, guess, attempt, success);
    }

    public SessionStartedEvent createSessionStartedEvent(final SessionDTO<?, ?, ?, ?> sessionDto) {
        var timestamp = createTimestamp();
        return new SessionStartedEvent(timestamp, sessionDto);
    }

    public SessionFinishedEvent createSessionFinishedEvent(final SessionDTO<?, ?, ?, ?> sessionDto) {
        var timestamp = createTimestamp();
        return new SessionFinishedEvent(timestamp, sessionDto);
    }

    public AudioPerfectPitchExercisePickedEvent createAudioPerfectPitchExercisePickedEvent(final AudioPerfectPitchExercise exercise) {
        var timestamp = createTimestamp();
        return new AudioPerfectPitchExercisePickedEvent(timestamp, exercise);
    }
}
