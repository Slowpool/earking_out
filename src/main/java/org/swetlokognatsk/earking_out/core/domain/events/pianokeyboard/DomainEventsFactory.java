package org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.NewPuzzleCreatedEvent;
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

    public NewPuzzleCreatedEvent createNewPuzzleDisplayedEvent(final Puzzle<?, ?> puzzle) {
        var timestamp = createTimestamp();
        return new NewPuzzleCreatedEvent(timestamp, puzzle);
    }

    public HintRepeatingRequestedEvent createHintRepeatingRequestedEvent(final Puzzle<?, ?> puzzle) {
        var timestamp = createTimestamp();
        return new HintRepeatingRequestedEvent(timestamp, puzzle);
    }

}
