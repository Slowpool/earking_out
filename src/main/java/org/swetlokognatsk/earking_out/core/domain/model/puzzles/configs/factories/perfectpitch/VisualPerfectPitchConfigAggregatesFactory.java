package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class VisualPerfectPitchConfigAggregatesFactory implements PuzzleConfigAggregatesFactory<VisualPerfectPitchConfigAggregate> {
    protected final PianoKeyboardRepository pianoKeyboardRepository;

    public VisualPerfectPitchConfigAggregatesFactory(final PianoKeyboardRepository pianoKeyboardRepository) {
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    public VisualPerfectPitchConfigAggregate createDefault() {
        return create(0, true, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO);
    }

    public VisualPerfectPitchConfigAggregate create(final int targetNumberOfPuzzles, final boolean statsRecording, final byte[] normalizedNotesForPuzzle, final Byte normalizedRootNote, final PerfectPitchInputMode inputMode) {
        return new VisualPerfectPitchConfigAggregate(new VisualPerfectPitchExercise(), targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, pianoKeyboardRepository);
    }

    public VisualPerfectPitchConfigAggregate createDeepCopy(VisualPerfectPitchConfigAggregate aggregate) {
        return create(aggregate.getTargetNumberOfPuzzles(), aggregate.getStatsRecording(), aggregate.getNormalizedNotesForPuzzle(), aggregate.getNormalizedRootNote(), aggregate.getInputMode());
    }
}
