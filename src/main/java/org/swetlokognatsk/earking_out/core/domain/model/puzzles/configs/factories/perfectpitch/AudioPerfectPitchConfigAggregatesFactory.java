package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import org.springframework.stereotype.Service;

@Service
public final class AudioPerfectPitchConfigAggregatesFactory extends PuzzleConfigAggregatesFactory<AudioPerfectPitchConfigAggregate> {

    public AudioPerfectPitchConfigAggregatesFactory(final ObjectCloner cloner, final UserResolver userResolver) {
        super(cloner, userResolver);
    }

    public AudioPerfectPitchConfigAggregate createDefault() {
        return create(10, true, new PianoKeyNumber[0], null, PerfectPitchInputMode.PIANO_ON_SCREEN, false);
    }

    public AudioPerfectPitchConfigAggregate create(final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano) {
        return new AudioPerfectPitchConfigAggregate(userResolver.getCurrentUserId(), AUDIO_PERFECT_PITCH_EXERCISE, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano);
    }
}
