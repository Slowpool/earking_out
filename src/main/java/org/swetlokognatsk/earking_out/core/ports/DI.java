package org.swetlokognatsk.earking_out.core.ports;

import org.swetlokognatsk.earking_out.app.desktop.services.AudioHintPlayer;
import org.swetlokognatsk.earking_out.app.desktop.services.AudioClipHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorServiceImpl;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators.SolutionGeneratorsFactory;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.AudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.VisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.core.ports.music.NoteNormalizer;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundPlayersFactory;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.session.services.ReadSessionService;
import org.swetlokognatsk.earking_out.core.ports.session.services.WriteSessionService;
import org.swetlokognatsk.earking_out.core.ports.sounds.PianoKeySounds;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.NoteNormalizerImpl;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.base.SerializationCloner;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.HintFinderDelegator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeAudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeVisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.InMemoryAudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.InMemoryVisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.FilePianoKeySoundPlayersFactory;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.InMemoryPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.MockPianoKeySoundPlayersFactory;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services.InMemoryReadSessionService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services.InMemoryWriteSessionService;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds.MockSoundPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds.PianoKeySoundsFromHints;

// TODO for now this class was made strictly in test purposes, to postpone DI in java
public final class DI {
    public static String TEST_ENV = "test_env";
    public static String PROD_ENV = "prod_env";
    public static String env = "test_env";

    // singleton lifetime simulation
    protected static InMemoryPuzzleConfigRepository inMemoryPuzzleConfigRepository;
    protected static PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory;
    protected static InMemoryPianoKeyboardRepository inMemoryPianoKeyboardRepository;

    private DI() {
    }

    public static <T> T get(Class<T> someClass, Object... args) {
        var className = someClass.getName();
        if (className.equals(NoteNormalizer.class.getName())) {
            return (T) new NoteNormalizerImpl();

        } else if (className.equals(HintFinder.class.getName())) {
            return (T) new HintFinderDelegator();
        } else if (className.equals(VisualPerfectPitchHints.class.getName())) {
            return (T) (env.equals(TEST_ENV) ? new FakeVisualPerfectPitchHints() : new InMemoryVisualPerfectPitchHints());
        } else if (className == AudioPerfectPitchHints.class.getName()) {
            return (T) (env.equals(TEST_ENV) ? new FakeAudioPerfectPitchHints() : new InMemoryAudioPerfectPitchHints());

        } else if (className.equals(WriteSessionService.class.getName())) {
            return (T) new InMemoryWriteSessionService();
        } else if (className.equals(ReadSessionService.class.getName())) {
            return (T) new InMemoryReadSessionService();

        } else if (className.equals(AudioPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (env.equals(TEST_ENV) ? new FakeAudioPerfectPitchSolutionGenerator() : new RandomAudioPerfectPitchSolutionGenerator((AudioPerfectPitchConfigDTO) args[0]));

        } else if (className.equals(AudioHintPlayer.class.getName())) {
            return (T) new AudioClipHintPlayer();

        } else if (className.equals(PianoKeySounds.class.getName())) {
            return (T) get(PianoKeySoundsFromHints.class);

        } else if (className.equals(PianoKeySoundsFromHints.class.getName())) {
            return (T) new PianoKeySoundsFromHints();

        } else if (className.equals(PianoKeyColorService.class.getName())) {
            return (T) new PianoKeyColorServiceImpl();

        } else if (className.equals(PuzzleConfigService.class.getName())) {
            return (T) new PuzzleConfigService(get(PuzzleConfigRepository.class));

        } else if (className.equals(PuzzleConfigRepository.class.getName())) {
            return (T) get(InMemoryPuzzleConfigRepository.class);

        } else if (className.equals(InMemoryPuzzleConfigRepository.class.getName())) {
            if (inMemoryPuzzleConfigRepository == null) {
                inMemoryPuzzleConfigRepository = new InMemoryPuzzleConfigRepository(get(PianoKeyboardRepository.class));
            }
            return (T) inMemoryPuzzleConfigRepository;

        } else if (className.equals(PianoKeyboardRepository.class.getName())) {
            return (T) get(InMemoryPianoKeyboardRepository.class);

        } else if (className.equals(InMemoryPianoKeyboardRepository.class.getName())) {
            if (inMemoryPianoKeyboardRepository == null) {
                inMemoryPianoKeyboardRepository = new InMemoryPianoKeyboardRepository(get(PianoKeyboardAggregatesFactory.class));
            }
            return (T) inMemoryPianoKeyboardRepository;

        } else if (className.equals(SessionService.class.getName())) {
            return (T) new SessionService(DI.get(PuzzleConfigRepository.class));

        } else if (className.equals(PianoKeyboardAggregatesFactory.class.getName())) {
            if (pianoKeyboardAggregatesFactory == null) {
                pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory();
            }
            return (T) pianoKeyboardAggregatesFactory;

        } else if (className.equals(PianoKeysFactory.class.getName())) {
            return (T) new PianoKeysFactory();

        } else if (className.equals(ObjectCloner.class.getName())) {
            return (T) new SerializationCloner();

        } else if (className.equals(PianoKeySoundPlayersFactory.class.getName())) {
            return (T) (env.equals(PROD_ENV) ? new FilePianoKeySoundPlayersFactory() : new MockPianoKeySoundPlayersFactory());

        } else if (className.equals(PuzzlesFactory.class.getName())) {
            return (T) new PuzzlesFactory();

        } else if (className.equals(SolutionGeneratorsFactory.class.getName())) {
            return (T) new SolutionGeneratorsFactory();

        } else {
            throw new IllegalArgumentException("DI dependency is not found: " + someClass.getName());
        }
    }

    // TODO wanna believe there's such a feature in SpringBoot. it's required for pure junit tests, so that each starts in the same DI-container state
    public static void clear() {
        inMemoryPuzzleConfigRepository = null;
        pianoKeyboardAggregatesFactory = null;
        inMemoryPianoKeyboardRepository = null;
    }
}
