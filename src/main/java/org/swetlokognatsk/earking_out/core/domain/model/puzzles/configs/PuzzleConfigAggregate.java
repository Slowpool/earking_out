package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.base.Aggregate;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfig;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig.*;

public class PuzzleConfigAggregate<PC extends PuzzleConfig<?>> extends Aggregate {

    protected PC puzzleConfig;
    protected final Map<PianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardAggregates;

    public PC getPuzzleConfig() {
        return puzzleConfig;
    }

    public PuzzleConfigAggregate(final PC puzzleConfig) {
        this.puzzleConfig = puzzleConfig;
        this.pianoKeyboardAggregates = findPianoKeyboardAggregates(puzzleConfig.exercise);
    }

    protected static Map<PianoKeyboardId, PianoKeyboardAggregate> findPianoKeyboardAggregates(final Exercise exercise) {
        // TODO it should be in helper class i think. switch(exercise) should be here
        var map = new HashMap<PianoKeyboardId, PianoKeyboardAggregate>();
        map.put(PianoKeyboardId.PERFECT_PITCH_NOTES_GUESSING, new PianoKeyboardAggregate(PianoKeyboardId.PERFECT_PITCH_NOTES_GUESSING));
        map.put(PianoKeyboardId.PERFECT_PITCH_NOTES_PICKER, new PianoKeyboardAggregate(PianoKeyboardId.PERFECT_PITCH_NOTES_PICKER));
        map.put(PianoKeyboardId.ROOT_NOTE_PICKER, new PianoKeyboardAggregate(PianoKeyboardId.ROOT_NOTE_PICKER));
        return map;
    }

    public String getId() {
        return puzzleConfig.getId();
    }

    public void updateViaPianoKeyPressing(final PianoKeyboardId pianoKeyboardId, final byte keyNumber) {
        var pianoKeyboard = pianoKeyboardAggregates.get(pianoKeyboardId);
        pianoKeyboard.pressKey(keyNumber);

        var propertyName = getPropertyName(pianoKeyboardId);
        updateProperty(propertyName, pianoKeyboard.getSelectedKeyNumbers());
    }

    protected String getPropertyName(final PianoKeyboardId pianoKeyboardId) {
        return switch (pianoKeyboardId) {
        case ROOT_NOTE_PICKER -> NORMALIZED_ROOT_NOTE_PROP;
        case PERFECT_PITCH_NOTES_PICKER -> NORMALIZED_NOTES_FOR_PUZZLE_PROP;
        default -> throw new IllegalArgumentException("this piano keyboard is not for config: " + pianoKeyboardId);
        };
    }

    // TODO it should be delegated to polymorphic descendants
    public void updateProperty(final String propertyName, final Object propertyValue) {
        switch (propertyName) {
        // TODO how 'bout reflection?
        case NORMALIZED_ROOT_NOTE_PROP: {
            var puzzleConfig = (AudioPerfectPitchConfig) this.puzzleConfig;
            puzzleConfig.normalizedNotesForPuzzle = (byte[]) propertyValue;
            this.puzzleConfig = (PC) puzzleConfig;
            break;
        }
        case NORMALIZED_NOTES_FOR_PUZZLE_PROP: {
            var puzzleConfig = (AudioPerfectPitchConfig) this.puzzleConfig;
            puzzleConfig.normalizedRootNote = ((byte[]) propertyValue)[0];
            this.puzzleConfig = (PC) puzzleConfig;
            // TODO is there any difference between `break; }` and `} break;` here?
            break;
        }
        default:
            throw new IllegalArgumentException("unknown puzzle config property: " + propertyName);
        }
    }

}
