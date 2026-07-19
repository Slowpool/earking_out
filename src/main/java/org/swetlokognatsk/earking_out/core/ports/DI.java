package org.swetlokognatsk.earking_out.core.ports;

import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PuzzlePanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.StatsPanesFactory;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.EndSessionAggregateDTOAssemblersFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators.SolutionGeneratorsFactory;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.sound.AudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.sound.SoundHarmonicIntervalHintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.core.ports.piano.PuzzleConfigPianoKeyboardStorageAdapter;
import org.swetlokognatsk.earking_out.core.ports.piano.SessionPianoKeyboardStorageAdapter;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.base.SerializationCloner;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.HintDemonstratorDelegator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.sound.PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.sound.AudioClipSoundHarmonicIntervalHintDemonstrator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.sound.FakeSoundHarmonicIntervalHintDemonstrator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.AudioClipPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.InMemoryPuzzleConfigPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.InMemorySessionPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.MockPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.PianoKeySoundFilesBuilder;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.TestInMemoryAllPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeVisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.RandomVisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.perfectpitch.InMemoryAudioPerfectPitchSessionRepository;

// TODO for now this class was made strictly in test purposes, to postpone DI in java
public final class DI {
    public static String TEST_ENV = "test_env";
    public static String PROD_ENV = "prod_env";
    public static String env = "test_env";

    // singleton lifetime simulation
    protected static InMemoryPuzzleConfigRepository inMemoryPuzzleConfigRepository;
    protected static PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory;
    protected static InMemoryPuzzleConfigPianoKeyboardRepository inMemoryPuzzleConfigPianoKeyboardRepository;
    protected static InMemorySessionPianoKeyboardRepository inMemorySessionPianoKeyboardRepository;
    protected static TestInMemoryAllPianoKeyboardRepository testInMemoryAllPianoKeyboardRepository;
    protected static AudioClipPianoKeySoundsPlayer audioClipPianoKeySoundsPlayer;
    protected static MockPianoKeySoundsPlayer mockPianoKeySoundsPlayer;
    protected static InMemoryAudioPerfectPitchSessionRepository inMemoryAudioPerfectPitchSessionRepository;

    private DI() {
    }

    public static <T> T get(Class<T> someClass, Object... args) {
        var className = someClass.getName();
        if (className.equals(NotesNormalizingService.class.getName())) {
            return (T) new NotesNormalizingService();

        } else if (className.equals(HintDemonstrator.class.getName())) {
            return (T) new HintDemonstratorDelegator();
        } else if (className == SoundHarmonicIntervalHintDemonstrator.class.getName()) {
            return (T) (env.equals(TEST_ENV) ? new FakeSoundHarmonicIntervalHintDemonstrator() : new AudioClipSoundHarmonicIntervalHintDemonstrator());

        } else if (className.equals(AudioPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (env.equals(TEST_ENV) ? new FakeAudioPerfectPitchSolutionGenerator() : new RandomAudioPerfectPitchSolutionGenerator((AudioPerfectPitchConfigDTO) args[0]));

        } else if (className.equals(VisualPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (env.equals(TEST_ENV) ? new FakeVisualPerfectPitchSolutionGenerator() : new RandomVisualPerfectPitchSolutionGenerator((VisualPerfectPitchConfigDTO) args[0]));

        } else if (className.equals(PianoKeyColorService.class.getName())) {
            return (T) new PianoKeyColorService();

        } else if (className.equals(PuzzleConfigService.class.getName())) {
            return (T) new PuzzleConfigService(get(PuzzleConfigRepository.class));

        } else if (className.equals(PuzzleConfigRepository.class.getName())) {
            return (T) get(InMemoryPuzzleConfigRepository.class);

        } else if (className.equals(InMemoryPuzzleConfigRepository.class.getName())) {
            if (inMemoryPuzzleConfigRepository == null) {
                inMemoryPuzzleConfigRepository = new InMemoryPuzzleConfigRepository(get(PuzzleConfigPianoKeyboardStorageAdapter.class));
            }
            return (T) inMemoryPuzzleConfigRepository;

        } else if (className.equals(PuzzleConfigPianoKeyboardStorageAdapter.class.getName())) {
            return (T) get(InMemoryPuzzleConfigPianoKeyboardRepository.class);

        } else if (className.equals(InMemoryPuzzleConfigPianoKeyboardRepository.class.getName())) {
            if (inMemoryPuzzleConfigPianoKeyboardRepository == null) {
                inMemoryPuzzleConfigPianoKeyboardRepository = new InMemoryPuzzleConfigPianoKeyboardRepository(get(PianoKeyboardAggregatesFactory.class));
            }
            return (T) inMemoryPuzzleConfigPianoKeyboardRepository;

        } else if (className.equals(SessionPianoKeyboardStorageAdapter.class.getName())) {
            return (T) get(InMemorySessionPianoKeyboardRepository.class);

        } else if (className.equals(InMemorySessionPianoKeyboardRepository.class.getName())) {
            if (inMemorySessionPianoKeyboardRepository == null) {
                inMemorySessionPianoKeyboardRepository = new InMemorySessionPianoKeyboardRepository(get(PianoKeyboardAggregatesFactory.class));
            }
            return (T) inMemorySessionPianoKeyboardRepository;

        } else if (className.equals(TestInMemoryAllPianoKeyboardRepository.class.getName())) {
            if (testInMemoryAllPianoKeyboardRepository == null) {
                testInMemoryAllPianoKeyboardRepository = new TestInMemoryAllPianoKeyboardRepository(get(PianoKeyboardAggregatesFactory.class));
            }
            return (T) testInMemoryAllPianoKeyboardRepository;

        } else if (className.equals(AudioPerfectPitchSessionService.class.getName())) {
            return (T) new AudioPerfectPitchSessionService(get(PuzzleConfigRepository.class), get(AudioPerfectPitchSessionRepository.class), get(SessionAggregatesFactory.class));

        } else if (className.equals(PianoKeyboardAggregatesFactory.class.getName())) {
            if (pianoKeyboardAggregatesFactory == null) {
                pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory();
            }
            return (T) pianoKeyboardAggregatesFactory;

        } else if (className.equals(PianoKeysFactory.class.getName())) {
            return (T) new PianoKeysFactory();

        } else if (className.equals(ObjectCloner.class.getName())) {
            return (T) get(SerializationCloner.class);

        } else if (className.equals(SerializationCloner.class.getName())) {
            return (T) new SerializationCloner();

        } else if (className.equals(PuzzlesFactory.class.getName())) {
            return (T) new PuzzlesFactory();

        } else if (className.equals(SolutionGeneratorsFactory.class.getName())) {
            return (T) new SolutionGeneratorsFactory();

        } else if (className.equals(SessionAggregatesFactory.class.getName())) {
            return (T) new SessionAggregatesFactory();

        } else if (className.equals(PuzzlePanesFactory.class.getName())) {
            return (T) new PuzzlePanesFactory();

        } else if (className.equals(HintDemonstrator.class.getName())) {
            return (T) new HintDemonstratorDelegator();

        } else if (className.equals(PianoKeySoundsPlayer.class.getName())) {
            return (T) (env.equals(TEST_ENV) ? get(MockPianoKeySoundsPlayer.class) : get(AudioClipPianoKeySoundsPlayer.class));

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

        } else if (className.equals(PianoKeySoundFilesBuilder.class.getName())) {
            return (T) new PianoKeySoundFilesBuilder();

        } else if (className.equals(AudioPerfectPitchHintDemonstrator.class.getName())) {
            return (T) get(PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator.class);

        } else if (className.equals(PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator.class.getName())) {
            return (T) new PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator(get(PianoKeySoundsPlayer.class));

        } else if (className.equals(AudioPerfectPitchSessionRepository.class.getName())) {
            return (T) get(InMemoryAudioPerfectPitchSessionRepository.class);

        } else if (className.equals(InMemoryAudioPerfectPitchSessionRepository.class.getName())) {
            if (inMemoryAudioPerfectPitchSessionRepository == null) {
                inMemoryAudioPerfectPitchSessionRepository = new InMemoryAudioPerfectPitchSessionRepository(get(SessionAggregatesFactory.class), get(SessionPianoKeyboardStorageAdapter.class));
            }
            return (T) inMemoryAudioPerfectPitchSessionRepository;

        } else if (className.equals(SessionRepositoryDelegator.class.getName())) {
            return (T) new SessionRepositoryDelegator();

        } else if (className.equals(EndSessionAggregateDTOAssemblersFactory.class.getName())) {
            return (T) new EndSessionAggregateDTOAssemblersFactory();

        } else if (className.equals(StatsPanesFactory.class.getName())) {
            return (T) new StatsPanesFactory(get(PuzzleConfigRepository.class), get(SessionRepositoryDelegator.class));

        } else {
            throw new IllegalArgumentException("DI dependency is not found: " + someClass.getName());
        }
    }

    // TODO wanna believe there's such a feature in SpringBoot. it's required for pure junit tests, so that each starts in the same DI-container state
    public static void deleteSingletons() {
        inMemoryPuzzleConfigRepository = null;
        pianoKeyboardAggregatesFactory = null;
        inMemoryPuzzleConfigPianoKeyboardRepository = null;
        inMemorySessionPianoKeyboardRepository = null;
        testInMemoryAllPianoKeyboardRepository = null;
        audioClipPianoKeySoundsPlayer = null;
        mockPianoKeySoundsPlayer = null;
        inMemoryAudioPerfectPitchSessionRepository = null;
    }
}
