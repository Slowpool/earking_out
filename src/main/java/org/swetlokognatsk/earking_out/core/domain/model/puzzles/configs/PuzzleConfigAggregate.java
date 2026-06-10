package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.base.Aggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig.*;

public class PuzzleConfigAggregate<PC extends PuzzleConfig<?>> extends Aggregate {

    protected PC puzzleConfig;
    protected final Map<PianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardAggregates = new HashMap<>();
    protected final PianoKeyboardRepository pianoKeyboardRepository;

    public PC getPuzzleConfig() {
        return puzzleConfig;
    }

    public PuzzleConfigAggregate(final PC puzzleConfig, final PianoKeyboardRepository pianoKeyboardRepository) {
        this.puzzleConfig = puzzleConfig;
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    public String getId() {
        return puzzleConfig.getId();
    }

    public void updateViaPianoKeyPressing(final PianoKeyboardId pianoKeyboardId, final byte keyNumber) {
        var pianoKeyboard = getPianoKeyboard(pianoKeyboardId);
        pianoKeyboard.pressKey(keyNumber);

        var propertyName = getPropertyName(pianoKeyboardId);
        updateProperty(propertyName, pianoKeyboard.getSelectedKeyNumbers());
    }

    // minor optimization. using create-if-not-exists strategy to avoid redundant writes of unchanged pianoKeyboards on `repository.save(this)`
    protected PianoKeyboardAggregate getPianoKeyboard(final PianoKeyboardId pianoKeyboardId) {
        if (!pianoKeyboardAggregates.containsKey(pianoKeyboardId)) {
            var pianoKeyboard = pianoKeyboardRepository.get(pianoKeyboardId);
            pianoKeyboardAggregates.put(pianoKeyboardId, pianoKeyboard);
        }
        return pianoKeyboardAggregates.get(pianoKeyboardId);
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
            break;
        }
        case NORMALIZED_NOTES_FOR_PUZZLE_PROP: {
            var puzzleConfig = (AudioPerfectPitchConfig) this.puzzleConfig;
            puzzleConfig.normalizedRootNote = ((byte[]) propertyValue)[0];
            // TODO is there any difference between `break; }` and `} break;` here?
            break;
        }
        default:
            throw new IllegalArgumentException("unknown puzzle config property: " + propertyName);
        }
    }

}
