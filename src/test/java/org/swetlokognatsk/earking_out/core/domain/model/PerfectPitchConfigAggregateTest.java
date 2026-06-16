package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

import static org.swetlokognatsk.earking_out.core.domain.model.piano.PianoKeyboardTestHelper.*;

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
        var pianoKeyboardId = PianoKeyboardId.ROOT_NOTE_PICKER;
        var configAggregate = getPerfectPitchAggregate();
        var pianoKeyboard = configAggregate.getPianoKeyboardAggregate(pianoKeyboardId);
        assertNoSelectedKeys(pianoKeyboard);

        Byte newRootNote = 4;
        configAggregate.updateViaPianoKeyPressing(pianoKeyboardId, newRootNote);

        assertOnlyThisKeyIsSelected(newRootNote, pianoKeyboard);
        assertEquals(1, pianoKeyboard.getSelectedKeyNumbers().length);
    }

    protected PerfectPitchConfigAggregate<?> getPerfectPitchAggregate() {
        var repository = DI.get(PuzzleConfigRepository.class);
        var aggregate = repository.get(new AudioPerfectPitchExercise());
        return (PerfectPitchConfigAggregate<?>) aggregate;
    }
}
