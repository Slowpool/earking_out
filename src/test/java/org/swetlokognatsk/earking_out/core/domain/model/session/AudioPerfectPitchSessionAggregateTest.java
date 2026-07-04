package org.swetlokognatsk.earking_out.core.domain.model.session;

import static org.junit.Assert.assertArrayEquals;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.FIRST_NOTE_NUMBER;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.ports.DI;

public class AudioPerfectPitchSessionAggregateTest {

    protected SessionAggregatesFactory sessionAggregatesFactory;

    @Before
    public void setup() {
        DI.deleteSingletons();
        sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
    }

    protected AudioPerfectPitchSessionAggregate createAggregate() {
        return sessionAggregatesFactory.create(new AudioPerfectPitchExercise());
    }

    @Test
    public void guessingPianoKeyboardDoesNotHaveSelectedNotesAfterCreate() {
        var aggregate = createAggregate();
        var guessingPianoKeyboard = aggregate.getGuessingPianoKeyboard();

        assertArrayEquals(new PianoKeyNumber[0], guessingPianoKeyboard.getSelectedKeyNumbers());

    }

    @Test
    public void guessViaPianoKeyPressingUpdatesKeyboard() {

        aggregate.guessViaPianoKeyPressing(FIRST_NOTE_NUMBER);
        var guessingPianoKeyboard = aggregate.getGuessingPianoKeyboard();

    }
}
