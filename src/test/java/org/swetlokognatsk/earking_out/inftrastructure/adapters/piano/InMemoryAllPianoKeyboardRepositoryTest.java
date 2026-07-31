package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.InMemoryRepositoryTest;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

@SpringBootTest
public final class InMemoryAllPianoKeyboardRepositoryTest extends InMemoryRepositoryTest<PianoKeyboardId, PianoKeyboardAggregate, TestInMemoryAllPianoKeyboardRepository> {
    protected TestInMemoryAllPianoKeyboardRepository repository;

    protected PianoKeyboardAggregate getSomeAggregate() {
        return repository.get(PianoKeyboardId.AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
    }

    protected TestInMemoryAllPianoKeyboardRepository getRepository() {
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
        DI.refreshDependencies();
        repository = DI.get(TestInMemoryAllPianoKeyboardRepository.class);
    }

    @Test
    @Deprecated
    // actually it's a wrong approach to test it, because it does not cover the whole """immutability""". the correct approach here is to check references of objects from `get()` before and after save - they must be different so that modifying some aggregate's state it'd not be immediately saved in the memory even afore `save()` call due to having the same reference to the same object.
    public void changeAggregatePropertyWithoutSave() {
        var rootNotePicker = repository.get(PianoKeyboardId.AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);

        rootNotePicker.touchKey(FIRST_NOTE_NUMBER);

        rootNotePicker = repository.get(PianoKeyboardId.AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
        assertArrayEquals(new PianoKeyNumber[0], rootNotePicker.getSelectedKeyNumbers());
    }

    /**
     * See
     * {@link org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.InMemoryAllPianoKeyboardRepositoryTest#changeAggregatePropertyWithoutSave}
     * regarding @Deprecated
     */
    @Test
    @Deprecated
    public void changeAggregatePropertyWithSave() {
        var rootNotePicker = repository.get(PianoKeyboardId.AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
        var someRootNote = FIRST_NOTE_NUMBER;

        rootNotePicker.touchKey(someRootNote);
        repository.save(rootNotePicker);

        rootNotePicker = repository.get(PianoKeyboardId.AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
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
