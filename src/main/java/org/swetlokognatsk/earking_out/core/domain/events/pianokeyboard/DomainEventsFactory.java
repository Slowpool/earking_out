package org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard;

import java.time.LocalDateTime;

import org.swetlokognatsk.earking_out.core.domain.events.puzzles.NewPuzzleDisplayedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;

public final class DomainEventsFactory {

    protected LocalDateTime createTimestamp() {
        return LocalDateTime.now();
    }

    public PianoKeyPressedEvent createPianoKeyPressedEvent(final PianoKeyNumber pianoKeyNumber) {
        var timestamp = createTimestamp();
        return new PianoKeyPressedEvent(timestamp, pianoKeyNumber);
    }

    public NewPuzzleDisplayedEvent createNewPuzzleDisplayedEvent(final Puzzle<?, ?> puzzle) {
        var timestamp = createTimestamp();
        return new NewPuzzleDisplayedEvent(timestamp, puzzle);
    }

}
