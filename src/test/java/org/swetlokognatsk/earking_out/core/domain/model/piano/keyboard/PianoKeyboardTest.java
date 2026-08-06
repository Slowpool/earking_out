package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import org.junit.Before;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

abstract class PianoKeyboardTest {
    private final PianoKeyboardId pianoKeyboardIdWithTestedMode = getSomeSuitablePianoKeyboardId();
    protected PianoKeyboardAggregate pianoKeyboard;

    protected static final PianoKeyNumber ANY_PIANO_KEY_NUMBER = FIRST_NOTE_NUMBER;

    @Before
    public void before() {
        DI.refreshDependencies();
        pianoKeyboard = createPianoKeyboard();
    }

    protected abstract PianoKeyboardId getSomeSuitablePianoKeyboardId();

    protected PianoKeyboardAggregate createPianoKeyboard() {
        return PianoKeyboardTestHelper.createPianoKeyboard(pianoKeyboardIdWithTestedMode);
    }

    protected PianoKeyboardAggregate createPianoKeyboard(final PianoKeyNumber[] selectedKeys) {
        return PianoKeyboardTestHelper.createPianoKeyboard(pianoKeyboardIdWithTestedMode, selectedKeys);
    }
}
