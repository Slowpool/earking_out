package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.swetlokognatsk.earking_out.core.domain.model.identity.User;

@Service
// TODO use it
// @Scope(WebApplicationContext.SCOPE_SESSION)
public final class AudioPerfectPitchConfigAggregatesFactory extends PuzzleConfigAggregatesFactory<AudioPerfectPitchConfigAggregate> {

    public AudioPerfectPitchConfigAggregatesFactory(final ObjectCloner cloner, final User user) {
        super(cloner, user);
    }

    public AudioPerfectPitchConfigAggregate createDefault() {
        return create(10, true, new PianoKeyNumber[0], null, PerfectPitchInputMode.PIANO_ON_SCREEN, false);
    }

    public AudioPerfectPitchConfigAggregate create(final int targetNumberOfPuzzles, final boolean statsRecording, final PianoKeyNumber[] normalizedNotesForPuzzle, final PianoKeyNumber normalizedRootNote, final PerfectPitchInputMode inputMode, final boolean soundlessGuessingPiano) {
        return new AudioPerfectPitchConfigAggregate(userId, AUDIO_PERFECT_PITCH_EXERCISE, targetNumberOfPuzzles, statsRecording, normalizedNotesForPuzzle, normalizedRootNote, inputMode, soundlessGuessingPiano);
    }
}
