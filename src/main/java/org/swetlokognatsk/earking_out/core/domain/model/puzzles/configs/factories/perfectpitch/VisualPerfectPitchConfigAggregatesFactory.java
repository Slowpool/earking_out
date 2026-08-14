package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

public final class VisualPerfectPitchConfigAggregatesFactory extends PuzzleConfigAggregatesFactory<VisualPerfectPitchConfigAggregate> {

    public VisualPerfectPitchConfigAggregatesFactory(final ObjectCloner cloner) {
        super(cloner);
    }

    public VisualPerfectPitchConfigAggregate createDefault() {
        return create(100, true, new PianoKeyNumber[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO, false);
    }

    public VisualPerfectPitchConfigAggregate create(final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano) {
        return new VisualPerfectPitchConfigAggregate(new VisualPerfectPitchExercise(), targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano);
    }
}
