package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class PianoKeyboardService {

    private final PianoKeyboardRepository repository;

    public PianoKeyboardService(final PianoKeyboardRepository repository) {
        this.repository = repository;
    }

    public void pressPianoKey(final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber pianoKeyNumber) {
        var pianoKeyboardAggregate = repository.get(pianoKeyboardId);
        try {
            pianoKeyboardAggregate.pressKey(pianoKeyNumber);
            repository.save(pianoKeyboardAggregate);
        }
        // TODO just Exception?
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void releasePianoKey(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboardAggregate = repository.get(pianoKeyboardId);
        try {
            pianoKeyboardAggregate.releaseKey();
            repository.save(pianoKeyboardAggregate);
        }
        // TODO just Exception?
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
