package org.swetlokognatsk.earking_out.core.domain.model.session;

import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.FIRST_NOTE_NUMBER;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
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

    protected AudioPerfectPitchSessionAggregate getAggregate() {
        return sessionAggregatesFactory.create(new AudioPerfectPitchExercise());
    }

    @Test
    public void asdf() {
        var aggregate = getAggregate();

        aggregate.guessViaPianoKeyPressing(FIRST_NOTE_NUMBER);

    }
}
