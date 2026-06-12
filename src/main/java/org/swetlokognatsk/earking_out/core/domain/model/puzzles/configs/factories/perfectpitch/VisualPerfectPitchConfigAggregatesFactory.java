package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class VisualPerfectPitchConfigAggregatesFactory extends PuzzleConfigAggregatesFactory<VisualPerfectPitchConfigAggregate> {
    protected final PianoKeyboardRepository pianoKeyboardRepository;

    public VisualPerfectPitchConfigAggregatesFactory(final PianoKeyboardRepository pianoKeyboardRepository) {
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    public VisualPerfectPitchConfigAggregate createDefault() {
        return new VisualPerfectPitchConfigAggregate(0, true, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO, pianoKeyboardRepository);
    }

    public VisualPerfectPitchConfigAggregate create(final int targetNumberOfPuzzles, final boolean statsRecording, final byte[] normalizedNotesForPuzzle, final Byte normalizedRootNote, final PerfectPitchInputMode inputMode) {
        return new VisualPerfectPitchConfigAggregate(targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, pianoKeyboardRepository);
    }

    public VisualPerfectPitchConfigAggregate createShallowCopy(VisualPerfectPitchConfigAggregate aggregate) {
        return create(aggregate.targetNumberOfPuzzles, aggregate.statsRecording, aggregate.normalizedNotesForPuzzle, aggregate.normalizedRootNote, aggregate.inputMode);
    }
}
