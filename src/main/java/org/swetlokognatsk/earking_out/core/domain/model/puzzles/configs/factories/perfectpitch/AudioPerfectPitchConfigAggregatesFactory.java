package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

public final class AudioPerfectPitchConfigAggregatesFactory extends PuzzleConfigAggregatesFactory<AudioPerfectPitchConfigAggregate> {
    // protected final PianoKeyboardRepository pianoKeyboardRepository;
    // // composition
    // protected final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory();

    public AudioPerfectPitchConfigAggregatesFactory(final ObjectCloner cloner) {
        super(cloner);
    }

    // // TODO why i specified pianoKeyboardRepository here?
    // public AudioPerfectPitchConfigAggregatesFactory(final PianoKeyboardRepository pianoKeyboardRepository) {
    //     this.pianoKeyboardRepository = pianoKeyboardRepository;
    // }

    public AudioPerfectPitchConfigAggregate createDefault() {
        return create(10, true, new PianoKeyNumber[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO, false);
    }

    public AudioPerfectPitchConfigAggregate create(final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano) {
        return new AudioPerfectPitchConfigAggregate(new AudioPerfectPitchExercise(), targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano);
    }
}
