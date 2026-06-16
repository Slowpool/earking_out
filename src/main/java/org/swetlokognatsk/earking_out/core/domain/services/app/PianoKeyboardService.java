package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class PianoKeyboardService {

    protected final PianoKeyboardRepository repository;

    public PianoKeyboardService(final PianoKeyboardRepository repository) {
        this.repository = repository;
    }

    // TODO it mustn't be used anywhere cuz everywhere it must be already handled by some another app service that has PianoKeyboardAggregate as child aggregate inside the use case
    public void pressKey(final PianoKeyboardId pianoKeyboardId, final byte keyNumber) {
        var pianoKeyboard = repository.get(pianoKeyboardId);

        try {
            pianoKeyboard.pressKey(keyNumber);
            repository.save(pianoKeyboard);
        }
        // TODO just Exception? what to do in catch{}?
        catch (Exception e) {
        }
    }

    public void releaseKey(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = repository.get(pianoKeyboardId);

        try {
            pianoKeyboard.releaseKey();
            repository.save(pianoKeyboard);
        }
        // TODO just Exception? what to do in catch{}?
        catch (Exception e) {
        }
    }
}
