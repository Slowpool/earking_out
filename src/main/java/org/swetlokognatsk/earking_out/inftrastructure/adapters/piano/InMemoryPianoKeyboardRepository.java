package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public class InMemoryPianoKeyboardRepository implements PianoKeyboardRepository {

    protected final Map<PianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardAggregates = new HashMap<>();
    protected final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory;

    public InMemoryPianoKeyboardRepository(final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory) {
        this.pianoKeyboardAggregatesFactory = pianoKeyboardAggregatesFactory;
        initPianoKeyboards();
    }

    protected void initPianoKeyboards() {
        PianoKeyboardAggregate pianoKeyboard;
        for (var pianoKeyboardId : PianoKeyboardId.values()) {
            pianoKeyboard = pianoKeyboardAggregatesFactory.create(pianoKeyboardId);
            pianoKeyboardAggregates.put(pianoKeyboardId, pianoKeyboard);
        }
    }

    protected PianoKeyboardAggregate getPianoKeyboardAggregate(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = pianoKeyboardAggregates.get(pianoKeyboardId);
        if (pianoKeyboard == null) {
            throw new IllegalArgumentException("piano keyboard with such an id is not found: " + pianoKeyboardId);
        }
        return pianoKeyboard;
    }

    public PianoKeyboardAggregate get(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = getPianoKeyboardAggregate(pianoKeyboardId);

        var pianoKeyboardCopy = pianoKeyboardAggregatesFactory.createDeepCopy(pianoKeyboard);
        return pianoKeyboardCopy;
    }

    public void save(final PianoKeyboardAggregate pianoKeyboardAggregate) {
        var pianoKeyboardId = pianoKeyboardAggregate.getPianoKeyboardId();
        // ensuring it exists
        getPianoKeyboardAggregate(pianoKeyboardId);

        var pianoKeyboardCopy = pianoKeyboardAggregatesFactory.createDeepCopy(pianoKeyboardAggregate);
        pianoKeyboardAggregates.put(pianoKeyboardId, pianoKeyboardCopy);
    }

    public PianoKeyboardDTO getViewDto(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = get(pianoKeyboardId);
        var dto = PianoKeyboardDtoAssembler.assemble(pianoKeyboard);
        return dto;
    }

}
