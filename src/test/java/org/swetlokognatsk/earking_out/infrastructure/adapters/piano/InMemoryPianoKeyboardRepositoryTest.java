package org.swetlokognatsk.earking_out.infrastructure.adapters.piano;

import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.*;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.OwnedPianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.infrastructure.adapters.InMemoryRepositoryTest;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

@EOSpringBootTest
@DirtiesContext
public final class InMemoryPianoKeyboardRepositoryTest extends InMemoryRepositoryTest<OwnedPianoKeyboardId, PianoKeyboardAggregate, InMemoryPianoKeyboardRepository> {
    private static final PianoKeyNumber someRootNote = PianoKeyNumber.valueOf(50);

    private InMemoryPianoKeyboardRepository repository;

    protected PianoKeyboardAggregate getSomeAggregate() {
        return repository.getInevitably(AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
    }

    protected InMemoryPianoKeyboardRepository getRepository() {
        return repository;
    }

    protected void makeMinorChange(final PianoKeyboardAggregate aggregate) {
        aggregate.touchKey(someRootNote);
    }

    /**
     * @param freshman - this aggregate is just got from repo.
     * @param suspect  - this aggregate was got from repo and then it was changed in
     *                 minor way
     */
    protected void assertAreDifferentByMinorChange(final PianoKeyboardAggregate freshman, final PianoKeyboardAggregate suspect) {
        assertFalse(ArrayUtils.contains(freshman.getSelectedKeyNumbers(), someRootNote));
        assertTrue(ArrayUtils.contains(suspect.getSelectedKeyNumbers(), someRootNote));
    }

    private UserId getCurrentUserId() {
        var userResolver = DI.get(UserResolver.class);
        return userResolver.getCurrentUserId();
    }

    private UserId getUserIdThatIsDifferentFrom(final UserId userId) {
        return new UserId(userId.id() + 1);
    }

    @BeforeEach
    public void setup() {
        repository = DI.get(InMemoryPianoKeyboardRepository.class);
    }

    @Deprecated
    @Test
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    // actually it's a wrong approach to test it, because it does not cover the whole """immutability""". the correct approach here is to check references of objects from `get()` before and after save - they must be different so that modifying some aggregate's state it'd not be immediately saved in the memory even afore `save()` call due to having the same reference to the same object.
    public void changeAggregatePropertyWithoutSave() {
        var rootNotePicker = repository.getInevitably(AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);

        rootNotePicker.touchKey(FIRST_NOTE_NUMBER);

        rootNotePicker = repository.get(AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
        assertArrayEquals(new PianoKeyNumber[0], rootNotePicker.getSelectedKeyNumbers());
    }

    /**
     * See
     * {@link org.swetlokognatsk.earking_out.infrastructure.adapters.piano.InMemoryPianoKeyboardRepositoryTest#changeAggregatePropertyWithoutSave}
     * regarding @Deprecated
     */
    @Test
    @Deprecated
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    public void changeAggregatePropertyWithSave() {
        var rootNotePicker = repository.getInevitably(AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
        var someRootNote = FIRST_NOTE_NUMBER;

        rootNotePicker.touchKey(someRootNote);
        repository.save(rootNotePicker);

        rootNotePicker = repository.get(AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER);
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

    @Test
    @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
    public void getInevitablyCreatesWhenAggregateDoesNotExist() {
        var pianoKeyboardId = AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER;
        try {
            repository.get(pianoKeyboardId);
            fail();
        } catch (AggregateNotFoundException e) {
        }

        var aggregate = repository.getInevitably(pianoKeyboardId);
        assertNotNull(aggregate);

        try {
            repository.get(pianoKeyboardId);
        } catch (AggregateNotFoundException e) {
            fail();
        }
    }

    @Test
    public void tryGetPianoKeyboardOfAnotherUser() {
        var pianoKeyboardId = AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER;
        var currentUserId = getCurrentUserId();
        var anotherUserId = getUserIdThatIsDifferentFrom(currentUserId);
        var anotherOwnedId = new OwnedPianoKeyboardId(pianoKeyboardId, anotherUserId);
        // create and save piano keyboard for current user in one command
        repository.getInevitably(pianoKeyboardId);

        PianoKeyboardAggregate stillOwnPianoKeyboard;
        try {
            stillOwnPianoKeyboard = repository.get(anotherOwnedId);
        } catch (AggregateNotFoundException e) {
            // does not matter whether another user's piano keyboard is not found or piano keyboard for current user was not saved, it's wrong behavior.
            fail();
            return;
        }

        assertEquals(currentUserId, stillOwnPianoKeyboard.getId().userId());

    }
}
