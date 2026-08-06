package org.swetlokognatsk.earking_out.core.domain.events;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

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

    public HintRepeatingRequestedEvent createHintRepeatingRequestedEvent(final Puzzle<?, ?> puzzle) {
        var timestamp = createTimestamp();
        return new HintRepeatingRequestedEvent(timestamp, puzzle);
    }

    public UserTriedToGuessPuzzleEvent createUserTriedToGuessPuzzleEvent(final SessionId sessionId, final int puzzleNumber, final Solution guess, final int attempt, final boolean success) {
        var timestamp = createTimestamp();
        return new UserTriedToGuessPuzzleEvent(timestamp, sessionId, puzzleNumber, guess, attempt, success);
    }

    public SessionStartedEvent createSessionStartedEvent(final SessionId sessionId) {
        var timestamp = createTimestamp();
        return new SessionStartedEvent(timestamp, sessionId);
    }

    public SessionFinishedEvent createSessionFinishedEvent(final SessionId sessionId) {
        var timestamp = createTimestamp();
        return new SessionFinishedEvent(timestamp, sessionId);
    }
}
