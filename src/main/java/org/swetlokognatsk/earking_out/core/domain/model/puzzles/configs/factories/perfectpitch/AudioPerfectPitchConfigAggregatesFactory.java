package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class AudioPerfectPitchConfigAggregatesFactory extends PuzzleConfigAggregatesFactory<AudioPerfectPitchConfigAggregate> {
    protected final PianoKeyboardRepository pianoKeyboardRepository;

    public AudioPerfectPitchConfigAggregatesFactory(final PianoKeyboardRepository pianoKeyboardRepository) {
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    public AudioPerfectPitchConfigAggregate createDefault() {
        return create(0, true, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO);
    }

    public AudioPerfectPitchConfigAggregate create(final int targetNumberOfPuzzles, final boolean statsRecording, final byte[] normalizedNotesForPuzzle, final Byte normalizedRootNote, final PerfectPitchInputMode inputMode) {
        return new AudioPerfectPitchConfigAggregate(new AudioPerfectPitchExercise(), targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, pianoKeyboardRepository);
    }

    public AudioPerfectPitchConfigAggregate createShallowCopy(final AudioPerfectPitchConfigAggregate aggregate) {
        return create(aggregate.getTargetNumberOfPuzzles(), aggregate.getStatsRecording(), aggregate.getNormalizedNotesForPuzzle(), aggregate.getNormalizedRootNote(), aggregate.getInputMode());
    }
}
