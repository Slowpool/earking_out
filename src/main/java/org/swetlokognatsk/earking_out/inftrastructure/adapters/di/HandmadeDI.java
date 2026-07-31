package org.swetlokognatsk.earking_out.inftrastructure.adapters.di;

import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.di.CustomDI;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.AudioClipPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.InMemorySessionPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.MockPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.TestInMemoryAllPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeVisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.RandomVisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.perfectpitch.InMemoryAudioPerfectPitchSessionRepository;

public final class HandmadeDI implements CustomDI {

    // singleton lifetime simulation
    protected static InMemoryPuzzleConfigRepository inMemoryPuzzleConfigRepository;
    protected static PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory;
    protected static InMemorySessionPianoKeyboardRepository inMemorySessionPianoKeyboardRepository;
    protected static TestInMemoryAllPianoKeyboardRepository testInMemoryAllPianoKeyboardRepository;
    protected static AudioClipPianoKeySoundsPlayer audioClipPianoKeySoundsPlayer;
    protected static MockPianoKeySoundsPlayer mockPianoKeySoundsPlayer;
    protected static InMemoryAudioPerfectPitchSessionRepository inMemoryAudioPerfectPitchSessionRepository;
    protected static DomainEventsFactory domainEventsFactory;
    protected static EventPublisher eventPublisher;
    protected static EventBus eventBus;

    public <T> T get(Class<T> someClass, Object... args) {

        var className = someClass.getName();
        // // TODO delete later
        // } else if (className == SoundHarmonicIntervalHintDemonstrator.class.getName()) {
        //     return (T) (env.equals(TEST_ENV) ? new FakeSoundHarmonicIntervalHintDemonstrator() : new AudioClipSoundHarmonicIntervalHintDemonstrator());

        if (className.equals(AudioPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (DI.isTestEnv() ? new FakeAudioPerfectPitchSolutionGenerator() : new RandomAudioPerfectPitchSolutionGenerator((AudioPerfectPitchConfigDTO) args[0]));

        } else if (className.equals(VisualPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (DI.isTestEnv() ? new FakeVisualPerfectPitchSolutionGenerator() : new RandomVisualPerfectPitchSolutionGenerator((VisualPerfectPitchConfigDTO) args[0]));

        } else if (className.equals(PianoKeySoundsPlayer.class.getName())) {
            return (T) (DI.isTestEnv() ? get(MockPianoKeySoundsPlayer.class) : get(AudioClipPianoKeySoundsPlayer.class));

        } else if (className.equals(MockPianoKeySoundsPlayer.class.getName())) {
            if (mockPianoKeySoundsPlayer == null) {
                mockPianoKeySoundsPlayer = new MockPianoKeySoundsPlayer();
            }
            return (T) mockPianoKeySoundsPlayer;

        } else if (className.equals(AudioClipPianoKeySoundsPlayer.class.getName())) {
            if (audioClipPianoKeySoundsPlayer == null) {
                audioClipPianoKeySoundsPlayer = new AudioClipPianoKeySoundsPlayer();
            }
            return (T) audioClipPianoKeySoundsPlayer;

        } else {
            throw new IllegalArgumentException("Custom DI dependency is not found: " + someClass.getName());
        }
    }

    public void refreshDependencies() {
        inMemoryPuzzleConfigRepository = null;
        pianoKeyboardAggregatesFactory = null;
        inMemorySessionPianoKeyboardRepository = null;
        testInMemoryAllPianoKeyboardRepository = null;
        audioClipPianoKeySoundsPlayer = null;
        mockPianoKeySoundsPlayer = null;
        inMemoryAudioPerfectPitchSessionRepository = null;
        domainEventsFactory = null;
        eventPublisher = null;
        eventBus = null;
    }

}
