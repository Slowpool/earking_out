package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;

public final class PerfectPitchConfigAggregateTest {

    @Test
    public void ensureRootNoteIsUpdated() {
        var aggregate = getPerfectPitchAggregate();
        assertEquals(null, aggregate.getNormalizedRootNote());

        Byte newRootNote = 4;
        aggregate.updateViaPianoKeyPressing(PianoKeyboardId.ROOT_NOTE_PICKER, newRootNote);

        assertEquals(newRootNote, aggregate.getNormalizedRootNote());
    }

    @Test
    public void ensureRootNoteUpdatingAlsoCausesPianoKeyboardUpdate() {
        var aggregate = getPerfectPitchAggregate();

        Byte newRootNote = 4;
        aggregate.updateViaPianoKeyPressing(PianoKeyboardId.ROOT_NOTE_PICKER, newRootNote);

    }

    protected PerfectPitchConfigAggregate<?> getPerfectPitchAggregate() {
        AudioPerfectPitchConfigAggregatesFactory factory = AbstractPuzzleConfigAggregatesFactory.createFactory(new AudioPerfectPitchExercise());
        var aggregate = factory.createDefault();
        return aggregate;
    }
}
