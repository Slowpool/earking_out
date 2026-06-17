package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class PianoKeyboardService {

    protected final PianoKeyboardRepository repository;

    public PianoKeyboardService(final PianoKeyboardRepository repository) {
        this.repository = repository;
    }

    public void releaseKey(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = repository.get(pianoKeyboardId);

        // try {
            pianoKeyboard.releaseKey();
            repository.save(pianoKeyboard);
        // }
        // // TODO just Exception? what to do in catch{}?
        // catch (Exception e) {
        // }
    }
}
