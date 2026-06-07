package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.DI;

public final class PianoKeyboardService {

    public void pressKey(final PianoKeyboardId pianoKeyboardId, final byte keyNumber) {
        var pianoKeyboardRepository = DI.get(PianoKeyboardRepository.class);
        var pianoKeyboard = pianoKeyboardRepository.get(pianoKeyboardId);

        try {
            pianoKeyboard.pressKey(keyNumber);
            pianoKeyboardRepository.save(pianoKeyboard);
        }
        // TODO just Exception? what to do in catch{}?
        catch (Exception e) {
        }
    }

    public void releaseKey(final PianoKeyboardId pianoKeyboardId, final byte keyNumber) {
        // TODO copy-past somewhere from PerfectPitchConfigPane
    }
}
