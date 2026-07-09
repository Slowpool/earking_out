package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.InMemoryRepositoryTest;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class InMemoryPianoKeyboardRepositoryTest extends InMemoryRepositoryTest<PianoKeyboardId, PianoKeyboardAggregate, InMemoryPianoKeyboardRepository> {
    protected InMemoryPianoKeyboardRepository repository;

    protected PianoKeyboardAggregate getSomeAggregate() {
        return repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
    }

    protected InMemoryPianoKeyboardRepository getRepository() {
        return repository;
    }

    protected void makeMinorChange(final PianoKeyboardAggregate aggregate) {
        aggregate.touchKey(someRootNote);
    }

    protected static final PianoKeyNumber someRootNote = PianoKeyNumber.valueOf(50);

    /**
     * @param freshman - this aggregate is just got from repo.
     * @param suspect  - this aggregate was got from repo and then it was changed in
     *                 minor way
     */
    protected void assertAreDifferentByMinorChange(final PianoKeyboardAggregate freshman, final PianoKeyboardAggregate suspect) {
        assertFalse(ArrayUtils.contains(freshman.getSelectedKeyNumbers(), someRootNote));
        assertTrue(ArrayUtils.contains(suspect.getSelectedKeyNumbers(), someRootNote));
    }

    @Before
    public void setup() {
        DI.deleteSingletons();
        repository = DI.get(InMemoryPianoKeyboardRepository.class);
    }

    @Test
    @Deprecated
    // actually it's a wrong approach to test it, because it does not cover the whole """immutability""". the correct approach here is to check references of objects from `get()` before and after save - they must be different so that modifying some aggregate's state it'd not be immediately saved in the memory even afore `save()` call due to having the same reference to the same object.
    public void changeAggregatePropertyWithoutSave() {
        var rootNotePicker = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);

        rootNotePicker.touchKey(FIRST_NOTE_NUMBER);

        rootNotePicker = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
        assertArrayEquals(new PianoKeyNumber[0], rootNotePicker.getSelectedKeyNumbers());
    }

    /**
     * See
     * {@link org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.InMemoryPianoKeyboardRepositoryTest#changeAggregatePropertyWithoutSave}
     * regarding @Deprecated
     */
    @Test
    @Deprecated
    public void changeAggregatePropertyWithSave() {
        var rootNotePicker = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
        var someRootNote = FIRST_NOTE_NUMBER;

        rootNotePicker.touchKey(someRootNote);
        repository.save(rootNotePicker);

        rootNotePicker = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
        assertArrayEquals(new PianoKeyNumber[] { someRootNote }, rootNotePicker.getSelectedKeyNumbers());
    }

    @Test
    public void ensureGetMethodGivesCopyWithoutSaveProxy() {
        ensureGetMethodGivesCopyWithoutSave();
    }

    @Test
    public void ensureGetMethodGivesCopyAfterSaveProxy() {
        ensureGetMethodGivesCopyAfterSave();
    }

    @Test
    public void ensureSaveMethodPersistsCopyProxy() {
        ensureSaveMethodPersistsCopy();
    }
}
