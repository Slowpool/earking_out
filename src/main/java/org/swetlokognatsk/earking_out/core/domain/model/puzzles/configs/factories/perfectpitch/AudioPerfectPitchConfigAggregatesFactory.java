package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class AudioPerfectPitchConfigAggregatesFactory implements PuzzleConfigAggregatesFactory<AudioPerfectPitchConfigAggregate, PerfectPitchConfigDependentAggregatesDTO> {
    protected final PianoKeyboardRepository pianoKeyboardRepository;
    // composition
    protected final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory();

    public AudioPerfectPitchConfigAggregatesFactory(final PianoKeyboardRepository pianoKeyboardRepository) {
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    public AudioPerfectPitchConfigAggregate createDefault(final PerfectPitchConfigDependentAggregatesDTO dependentAggregates) {
        return create(0, true, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO, dependentAggregates.pianoKeyboardAggregates);
    }

    public AudioPerfectPitchConfigAggregate create(final int targetNumberOfPuzzles, final boolean statsRecording, final byte[] normalizedNotesForPuzzle, final Byte normalizedRootNote, final PerfectPitchInputMode inputMode, final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        return new AudioPerfectPitchConfigAggregate(new AudioPerfectPitchExercise(), targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, pianoKeyboardAggregates);
    }

    public AudioPerfectPitchConfigAggregate createDeepCopy(final AudioPerfectPitchConfigAggregate aggregate) {
        var oldNormalizedNotes = aggregate.getNormalizedNotesForPuzzle();
        var normalizedNotesForPuzzleCopy = Arrays.copyOf(oldNormalizedNotes, oldNormalizedNotes.length);
        var pianoKeyboardAggregates = aggregate.pianoKeyboardAggregates.values().toArray(PianoKeyboardAggregate[]::new);
        PianoKeyboardAggregate[] pianoKeyboardAggregatesCopy = pianoKeyboardAggregatesFactory.createDeepCopy(pianoKeyboardAggregates);
        return create(aggregate.getTargetNumberOfPuzzles(), aggregate.getStatsRecording(), normalizedNotesForPuzzleCopy, aggregate.getNormalizedRootNote(), aggregate.getInputMode(), pianoKeyboardAggregatesCopy);
    }
}
