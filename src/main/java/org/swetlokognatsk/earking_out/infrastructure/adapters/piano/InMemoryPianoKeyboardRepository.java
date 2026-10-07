package org.swetlokognatsk.earking_out.infrastructure.adapters.piano;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.AggregateRepository;
import lombok.AccessLevel;
import lombok.Getter;

@Repository
@Getter(AccessLevel.PRIVATE)
public class InMemoryPianoKeyboardRepository extends AggregateRepository implements PianoKeyboardRepository {

    private final Map<PianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardAggregates = new HashMap<>();
    private final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory;
    private final PianoKeyboardDtoAssembler pianoKeyboardDtoAssembler;

    public InMemoryPianoKeyboardRepository(final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory, final PianoKeyboardDtoAssembler pianoKeyboardDtoAssembler) {
        this.pianoKeyboardAggregatesFactory = pianoKeyboardAggregatesFactory;
        this.pianoKeyboardDtoAssembler = pianoKeyboardDtoAssembler;
        initPianoKeyboards();
    }

    private void initPianoKeyboards() {
        PianoKeyboardAggregate pianoKeyboard;
        for (var pianoKeyboardId : PianoKeyboardId.values()) {
            pianoKeyboard = getPianoKeyboardAggregatesFactory()
                    .create(pianoKeyboardId);
            getPianoKeyboardAggregates()
                    .put(pianoKeyboardId, pianoKeyboard);
        }
    }

    protected final PianoKeyboardAggregate getPianoKeyboardAggregate(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = getPianoKeyboardAggregates()
                .get(pianoKeyboardId);
        if (pianoKeyboard == null) {
            throw new IllegalArgumentException("piano keyboard with such an id is not found: " + pianoKeyboardId);
        }
        return pianoKeyboard;
    }

    public final PianoKeyboardAggregate get(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = getPianoKeyboardAggregate(pianoKeyboardId);

        var pianoKeyboardCopy = getPianoKeyboardAggregatesFactory()
                .createDeepCopy(pianoKeyboard);
        return pianoKeyboardCopy;
    }

    // TODO generalize the whole set/get logic into `InMemoryAggregateRepository` abstract class
    public final void save(final PianoKeyboardAggregate pianoKeyboardAggregate) {
        var pianoKeyboardId = pianoKeyboardAggregate.getPianoKeyboardId();
        // ensuring it exists (keyboards are initialized in initKeyboards(). further no new keyboards can be created)
        getPianoKeyboardAggregate(pianoKeyboardId);

        var events = pianoKeyboardAggregate.flushEvents();

        var pianoKeyboardCopy = getPianoKeyboardAggregatesFactory()
                .createDeepCopy(pianoKeyboardAggregate);
        getPianoKeyboardAggregates()
                .put(pianoKeyboardId, pianoKeyboardCopy);

        publishEvents(events);
    }

    public final PianoKeyboardDTO getPianoKeyboardDTO(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = get(pianoKeyboardId);
        var dto = getPianoKeyboardDtoAssembler()
                .assemble(pianoKeyboard);
        return dto;
    }

    // // TODO is it used anywhere?
    // public final PianoKeyboardAggregate[] getByExercise(final Exercise exercise) {
    //     // TODO refactoring. add PianoKeyboardType (session/puzzleConfig)
    //     var pianoKeyboardIds = PianoKeyboardId.getPianoKeyboardIds(exercise);
    //     var stream = Arrays.stream(pianoKeyboardIds);
    //     stream = stream.filter((PianoKeyboardId pianoKeyboardId) -> ArrayUtils.contains(PianoKeyboardIds(), pianoKeyboardId));
    //     var pianoKeyboardsStream = stream.map((PianoKeyboardId pianoKeyboardId) -> get(pianoKeyboardId));
    //     var pianoKeyboards = pianoKeyboardsStream.toArray(PianoKeyboardAggregate[]::new);
    //     return pianoKeyboards;
    // }

}
