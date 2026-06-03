package org.swetlokognatsk.earking_out.core.domain.model.piano;

import org.junit.Before;

abstract class PianoKeyboardTest {
    private PianoKeyboardId pianoKeyboardIdWithTestedMode = getSomeSuitablePianoKeyboardId();
    protected PianoKeyboardAggregate pianoKeyboard;

    protected abstract PianoKeyboardId getSomeSuitablePianoKeyboardId();

    protected PianoKeyboardAggregate createPianoKeyboard() {
        return PianoKeyboardTestHelper.createPianoKeyboard(pianoKeyboardIdWithTestedMode);
    }

    protected PianoKeyboardAggregate createPianoKeyboard(byte[] selectedKeys) {
        return PianoKeyboardTestHelper.createPianoKeyboard(pianoKeyboardIdWithTestedMode, selectedKeys);
    }

    @Before
    public void before() {
        pianoKeyboard = createPianoKeyboard();
    }
}
