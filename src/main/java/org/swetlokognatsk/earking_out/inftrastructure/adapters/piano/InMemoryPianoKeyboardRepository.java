package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardSoundMode;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardStorageAdapter;


abstract class InMemoryPianoKeyboardRepository implements PianoKeyboardStorageAdapter {

    protected final Map<PianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardAggregates = new HashMap<>();
    protected final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory;

    protected abstract PianoKeyboardId[] getPianoKeyboardIds();

    public InMemoryPianoKeyboardRepository(final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory) {
        this.pianoKeyboardAggregatesFactory = pianoKeyboardAggregatesFactory;
        initPianoKeyboards();
    }

    protected void initPianoKeyboards() {
        PianoKeyboardAggregate pianoKeyboard;
        for (var pianoKeyboardId : getPianoKeyboardIds()) {
            // TODO pull soundMode from puzzleConfig
            pianoKeyboard = pianoKeyboardAggregatesFactory.create(pianoKeyboardId, PianoKeyboardSoundMode.SOUNDLESS);
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
        // ensuring it exists (keyboards are initialized in initKeyboards(). further no new keyboards can be created)
        getPianoKeyboardAggregate(pianoKeyboardId);

        var events = pianoKeyboardAggregate.releaseEvents();

        var pianoKeyboardCopy = pianoKeyboardAggregatesFactory.createDeepCopy(pianoKeyboardAggregate);
        pianoKeyboardAggregates.put(pianoKeyboardId, pianoKeyboardCopy);
        // TODO publishEvents() method? abstract Repository class?
        var eventPublisher = DI.get(EventPublisher.class);
        eventPublisher.publish(events);
    }

    public PianoKeyboardDTO getViewDto(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = get(pianoKeyboardId);
        var dto = PianoKeyboardDtoAssembler.assemble(pianoKeyboard);
        return dto;
    }

    public PianoKeyboardAggregate[] getByExercise(final Exercise exercise) {
        // TODO refactoring. add PianoKeyboardType (session/puzzleConfig)
        var pianoKeyboardIds = PianoKeyboardId.getPianoKeyboardIds(exercise);
        var stream = Arrays.stream(pianoKeyboardIds);
        stream = stream.filter((PianoKeyboardId pianoKeyboardId) -> ArrayUtils.contains(getPianoKeyboardIds(), pianoKeyboardId));
        var pianoKeyboardsStream = stream.map((PianoKeyboardId pianoKeyboardId) -> get(pianoKeyboardId));
        var pianoKeyboards = pianoKeyboardsStream.toArray(PianoKeyboardAggregate[]::new);
        return pianoKeyboards;
    }

}
