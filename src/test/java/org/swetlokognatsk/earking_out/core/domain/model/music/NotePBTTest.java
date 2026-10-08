package org.swetlokognatsk.earking_out.core.domain.model.music;

import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.LAST_NOTE_NUMBER;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

@SpringBootTest
public class NotePBTTest {

    // because they are minority
    private static List<Integer> PIANO_KEYS_WITHOUT_WHITE_KEY_NEIGHBOURS = List.of(3, 8, 10);

    @Provide
    private Arbitrary<PianoKeyNumber> getAllPianoKeyNumbers() {
        return Arbitraries.of(PianoKeyNumber.getAll());
    }

    @Property
    public void pianoKeyNumberWithoutNatural(@ForAll("getAllPianoKeyNumbers") final PianoKeyNumber pianoKeyNumber) {
        var notes = Note.denormalize(pianoKeyNumber, true);

        var expectedNumberOfNotes = getExpectedNumberOfNotes(pianoKeyNumber);
        assertEquals(expectedNumberOfNotes, notes.length);
        for (int i = 0; i < expectedNumberOfNotes; i++) {
            var normalizedNote = notes[i].normalize();
            assertTrue(normalizedNote.equals(pianoKeyNumber));
        }
        if (expectedNumberOfNotes == 2) {
            assertFalse(notes[0].equals(notes[1]));
        }
    }

    // in app domain, if any piano key (except the corner ones) has a white piano key as a neighbour, then it has 2 ways to represent it as a note. otherwise - only one. double sharps and flats aren out of app domain.
    private int getExpectedNumberOfNotes(final PianoKeyNumber pianoKeyNumber) {
        if (pianoKeyNumber.equals(FIRST_NOTE_NUMBER)
                || pianoKeyNumber.equals(LAST_NOTE_NUMBER)) {
            return 1;
        }

        var octaveScopedNumber = pianoKeyNumber.octaveScopedKeyNumber;
        return PIANO_KEYS_WITHOUT_WHITE_KEY_NEIGHBOURS.contains(Integer.valueOf(octaveScopedNumber))
                ? 1
                : 2;
    }
}
