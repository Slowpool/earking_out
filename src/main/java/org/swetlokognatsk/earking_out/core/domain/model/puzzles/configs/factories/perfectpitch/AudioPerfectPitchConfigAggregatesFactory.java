package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardStorageAdapter;

public final class AudioPerfectPitchConfigAggregatesFactory extends PuzzleConfigAggregatesFactory<AudioPerfectPitchConfigAggregate, PerfectPitchConfigDependentAggregatesDTO> {
    // protected final PianoKeyboardStorageAdapter pianoKeyboardRepository;
    // // composition
    // protected final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory();

    public AudioPerfectPitchConfigAggregatesFactory(final ObjectCloner cloner) {
        super(cloner);
    }

    // // TODO why i specified pianoKeyboardRepository here?
    // public AudioPerfectPitchConfigAggregatesFactory(final PianoKeyboardStorageAdapter pianoKeyboardRepository) {
    //     this.pianoKeyboardRepository = pianoKeyboardRepository;
    // }

    public AudioPerfectPitchConfigAggregate createDefault(final PerfectPitchConfigDependentAggregatesDTO dependentAggregates) {
        return create(10, true, new PianoKeyNumber[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO, false, dependentAggregates.pianoKeyboardAggregates);
    }

    public AudioPerfectPitchConfigAggregate create(final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano, final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        return new AudioPerfectPitchConfigAggregate(new AudioPerfectPitchExercise(), targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano, pianoKeyboardAggregates);
    }
}
