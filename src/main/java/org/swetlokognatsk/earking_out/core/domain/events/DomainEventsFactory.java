package org.swetlokognatsk.earking_out.core.domain.events;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.exercises.AudioPerfectPitchExercisePickedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public final class DomainEventsFactory {

    private LocalDateTime createTimestamp() {
        return LocalDateTime.now();
    }

    public PianoKeyPressedEvent createPianoKeyPressedEvent(final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber pianoKeyNumber) {
        var timestamp = createTimestamp();
        return new PianoKeyPressedEvent(timestamp, pianoKeyboardId, pianoKeyNumber);
    }

    public NewPuzzleCreatedEvent createNewpuzzleCreatedEvent(final SessionId sessionId, final Puzzle<?, ?> puzzle) {
        var timestamp = createTimestamp();
        return new NewPuzzleCreatedEvent(timestamp, sessionId, puzzle);
    }

    public HintRepeatingRequestedEvent createHintRepeatingRequestedEvent(final SessionId sessionId, final Puzzle<?, ?> puzzle) {
        var timestamp = createTimestamp();
        return new HintRepeatingRequestedEvent(timestamp, sessionId, puzzle);
    }

    public UserTriedToGuessPuzzleEvent createUserTriedToGuessPuzzleEvent(final SessionId sessionId, final int puzzleNumber, final Solution guess, final int attempt, final boolean success) {
        var timestamp = createTimestamp();
        return new UserTriedToGuessPuzzleEvent(timestamp, sessionId, puzzleNumber, guess, attempt, success);
    }

    public SessionStartedEvent createSessionStartedEvent(final SessionId sessionId, final PuzzleConfigDTO<?> puzzleConfig) {
        var timestamp = createTimestamp();
        return new SessionStartedEvent(timestamp, sessionId, puzzleConfig);
    }

    public SessionFinishedEvent createSessionFinishedEvent(final SessionId sessionId) {
        return createSessionFinishedEvent(sessionId, false);
    }

    public SessionFinishedEvent createSessionFinishedEvent(final SessionId sessionId, final boolean isAborted) {
        var timestamp = createTimestamp();
        return new SessionFinishedEvent(timestamp, sessionId, isAborted);
    }

    public AudioPerfectPitchExercisePickedEvent createAudioPerfectPitchExercisePickedEvent(final AudioPerfectPitchExercise exercise) {
        var timestamp = createTimestamp();
        return new AudioPerfectPitchExercisePickedEvent(timestamp, exercise);
    }
}
