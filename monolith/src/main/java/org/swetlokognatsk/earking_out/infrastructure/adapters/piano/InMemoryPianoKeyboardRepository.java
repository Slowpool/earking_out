package org.swetlokognatsk.earking_out.infrastructure.adapters.piano;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.OwnedPianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.AggregateRepository;
import lombok.AccessLevel;
import lombok.Getter;

@Repository
@Getter(AccessLevel.PRIVATE)
public class InMemoryPianoKeyboardRepository extends AggregateRepository implements PianoKeyboardRepository {

    private final Map<OwnedPianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardAggregates = new HashMap<>();
    private final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory;
    private final PianoKeyboardDtoAssembler pianoKeyboardDtoAssembler;
    private final UserResolver userResolver;

    public InMemoryPianoKeyboardRepository(final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory, final PianoKeyboardDtoAssembler pianoKeyboardDtoAssembler, final UserResolver userResolver) {
        this.pianoKeyboardAggregatesFactory = pianoKeyboardAggregatesFactory;
        this.pianoKeyboardDtoAssembler = pianoKeyboardDtoAssembler;
        this.userResolver = userResolver;
    }

    @Deprecated(forRemoval = true)
    private void initPianoKeyboards() {
        PianoKeyboardAggregate pianoKeyboard;
        for (var pianoKeyboardId : PianoKeyboardId.values()) {
            // pianoKeyboard = getPianoKeyboardAggregatesFactory()
            //         .create(pianoKeyboardId);
            // getPianoKeyboardAggregates()
            //         .put(pianoKeyboardId, pianoKeyboard);
        }
    }

    private OwnedPianoKeyboardId buildOwnedId(final PianoKeyboardId pianoKeyboardId) {
        return new OwnedPianoKeyboardId(pianoKeyboardId, userResolver.getCurrentUserId());
    }

    protected PianoKeyboardAggregate getPianoKeyboardAggregate(final PianoKeyboardId pianoKeyboardId) {
        var ownedId = buildOwnedId(pianoKeyboardId);
        var pianoKeyboard = getPianoKeyboardAggregates()
                .get(ownedId);

        if (pianoKeyboard == null) {
            throw new AggregateNotFoundException("piano keyboard with such an id is not found: " + ownedId);
        }

        return pianoKeyboard;
    }

    public PianoKeyboardAggregate get(final OwnedPianoKeyboardId ownedPianoKeyboardId) {
        // this behavior intentionally prohibits accessing other users' piano keyboards making it physically impossible
        return get(ownedPianoKeyboardId.pianoKeyboardId());
    }

    public PianoKeyboardAggregate get(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = getPianoKeyboardAggregate(pianoKeyboardId);

        var pianoKeyboardCopy = getPianoKeyboardAggregatesFactory()
                .createDeepCopy(pianoKeyboard);
        return pianoKeyboardCopy;
    }

    public PianoKeyboardAggregate getInevitably(final PianoKeyboardId pianoKeyboardId) {
        PianoKeyboardAggregate aggregate;
        try {
            aggregate = get(pianoKeyboardId);
        } catch (AggregateNotFoundException e) {
            aggregate = create(pianoKeyboardId);
            save(aggregate);
        }
        return aggregate;
    }

    private PianoKeyboardAggregate create(final PianoKeyboardId pianoKeyboardId) {
        var ownedId = buildOwnedId(pianoKeyboardId);
        return getPianoKeyboardAggregatesFactory()
                .create(ownedId);
    }

    // TODO generalize the whole set/get logic into `InMemoryAggregateRepository` abstract class
    // now it's physically possible to save piano keyboard of another user. though logically app service layer does not have an opportunity to obtain another user's piano keyboard, or change user id of obtained piano keyboard, so it's veeeery tricky (probably even impossible) to abuse saving of piano keyboard belonging to another user.
    public void save(final PianoKeyboardAggregate pianoKeyboardAggregate) {
        var events = pianoKeyboardAggregate.flushEvents();

        var ownedId = pianoKeyboardAggregate.getId();
        var pianoKeyboardCopy = getPianoKeyboardAggregatesFactory()
                .createDeepCopy(pianoKeyboardAggregate);
        getPianoKeyboardAggregates()
                .put(ownedId, pianoKeyboardCopy);

        publishEvents(events);
    }

    public PianoKeyboardDTO getPianoKeyboardDTO(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = get(pianoKeyboardId);
        var dto = getPianoKeyboardDtoAssembler()
                .assemble(pianoKeyboard);
        return dto;
    }

    public PianoKeyboardDTO getPianoKeyboardDTOInevitably(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboard = getInevitably(pianoKeyboardId);
        var dto = getPianoKeyboardDtoAssembler()
                .assemble(pianoKeyboard);
        return dto;
    }

}
