package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

// TODO dry violation (audio version is almost the same)
public final class VisualPerfectPitchConfigAggregatesFactory implements PuzzleConfigAggregatesFactory<VisualPerfectPitchConfigAggregate, PerfectPitchConfigDependentAggregatesDTO> {
    protected final PianoKeyboardRepository pianoKeyboardRepository;
    protected final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory();

    public VisualPerfectPitchConfigAggregatesFactory(final PianoKeyboardRepository pianoKeyboardRepository) {
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    public VisualPerfectPitchConfigAggregate createDefault(final PerfectPitchConfigDependentAggregatesDTO dependentAggregates) {
        return create(0, true, new PianoKeyNumber[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO, dependentAggregates.pianoKeyboardAggregates);
    }

    public VisualPerfectPitchConfigAggregate create(final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        return new VisualPerfectPitchConfigAggregate(new VisualPerfectPitchExercise(), targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, pianoKeyboardAggregates);
    }

    public VisualPerfectPitchConfigAggregate createDeepCopy(VisualPerfectPitchConfigAggregate aggregate) {
        var pianoKeyboardAggregates = aggregate.pianoKeyboardAggregates.values().toArray(PianoKeyboardAggregate[]::new);
        var pianoKeyboardAggregatesCopy = pianoKeyboardAggregatesFactory.createDeepCopy(pianoKeyboardAggregates);
        return create(aggregate.getTargetNumberOfPuzzles(), aggregate.getStatsRecording(), aggregate.getNormalizedNotesForPuzzle(), aggregate.getNormalizedRootNote(), aggregate.getInputMode(), pianoKeyboardAggregatesCopy);
    }
}
