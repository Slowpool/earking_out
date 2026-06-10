package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public class InMemoryPianoKeyboardRepository implements PianoKeyboardRepository {

    protected final Map<PianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardAggregates = new HashMap<>();

    public InMemoryPianoKeyboardRepository() {
        initPianoKeyboards();
    }

    protected void initPianoKeyboards() {
        PianoKeyboardAggregate pianoKeyboard;
        for (var pianoKeyboardId : PianoKeyboardId.values()) {
            pianoKeyboard = new PianoKeyboardAggregate(pianoKeyboardId);
            pianoKeyboardAggregates.put(pianoKeyboardId, pianoKeyboard);
        }
    }

    public PianoKeyboardAggregate get(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = pianoKeyboardAggregates.get(pianoKeyboardId);
        if (pianoKeyboard == null) {
            throw new IllegalArgumentException("piano keyboard with such an id is not found: " + pianoKeyboardId);
        }
        return pianoKeyboard;
    }

    public void save(final PianoKeyboardAggregate pianoKeyboardAggregate) {
        var pianoKeyboardId = pianoKeyboardAggregate.getPianoKeyboardId();
        var pianoKeyboard = pianoKeyboardAggregates.get(pianoKeyboardId);
        if (pianoKeyboard == null) {
            throw new IllegalArgumentException("piano keyboard with such an id is not found: " + pianoKeyboardId);
        }
        pianoKeyboardAggregates.put(pianoKeyboardId, pianoKeyboard);
    }

}
