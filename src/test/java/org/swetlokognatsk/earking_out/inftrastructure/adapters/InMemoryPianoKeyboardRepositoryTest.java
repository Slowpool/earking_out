package org.swetlokognatsk.earking_out.inftrastructure.adapters;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.InMemoryPianoKeyboardRepository;

public final class InMemoryPianoKeyboardRepositoryTest {
    protected InMemoryPianoKeyboardRepository repository;

    @Before
    public void setup() {
        repository = new InMemoryPianoKeyboardRepository();
    }

    @Test
    @Deprecated
    // actually it's a wrong approach to test it, because it does not cover the whole """immutability""". the correct approach here is to check references of objects from `get()` before and after save - they must be different so that modifying some aggregate's state it'd not be immediately saved in the memory even afore `save()` call due to having the same reference to the same object.
    public void changeAggregatePropertyWithoutSave() {
        var rootNotePicker = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);

        rootNotePicker.touchKey((byte) 4);

        rootNotePicker = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
        assertArrayEquals(new byte[0], rootNotePicker.getSelectedKeyNumbers());
    }

    /**
     * See
     * {@link org.swetlokognatsk.earking_out.inftrastructure.adapters.InMemoryPianoKeyboardRepositoryTest#changeAggregatePropertyWithoutSave}
     * regarding @Deprecated
     */
    @Test
    @Deprecated
    public void changeAggregatePropertyWithSave() {
        var rootNotePicker = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
        var someRootNote = (byte) 4;

        rootNotePicker.touchKey(someRootNote);
        repository.save(rootNotePicker);

        rootNotePicker = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
        assertArrayEquals(new byte[] { someRootNote }, rootNotePicker.getSelectedKeyNumbers());
    }

    @Test
    public void ensureReferentialConsistencyWithoutSave() {
        var rootNotePicker1 = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
        var rootNotePicker2 = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
        assertNotEquals(rootNotePicker1, rootNotePicker2);
    }

    @Test
    public void ensureReferentialConsistencyWithSave() {
        var rootNotePicker1 = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
        repository.save(rootNotePicker1);
        var rootNotePicker2 = repository.get(PianoKeyboardId.ROOT_NOTE_PICKER);
        assertNotEquals(rootNotePicker1, rootNotePicker2);
    }
}
