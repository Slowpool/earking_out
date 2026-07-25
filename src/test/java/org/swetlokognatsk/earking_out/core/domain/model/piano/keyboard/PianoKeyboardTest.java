package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.junit.Before;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.DI;

abstract class PianoKeyboardTest {
    private final PianoKeyboardId pianoKeyboardIdWithTestedMode = getSomeSuitablePianoKeyboardId();
    protected PianoKeyboardAggregate pianoKeyboard;

    @Before
    public void before() {
        DI.deleteSingletons();
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
