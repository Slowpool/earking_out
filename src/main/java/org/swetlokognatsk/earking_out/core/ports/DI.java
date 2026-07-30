package org.swetlokognatsk.earking_out.core.ports;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.support.GenericApplicationContext;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PuzzlePanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.StatsPanesFactory;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.EndSessionAggregateDTOAssemblersFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators.SolutionGeneratorsFactory;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;
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
import org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring.SpringEventBus;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring.SpringEventPublisher;
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
    protected static InMemorySessionPianoKeyboardRepository inMemorySessionPianoKeyboardRepository;
    protected static TestInMemoryAllPianoKeyboardRepository testInMemoryAllPianoKeyboardRepository;
    protected static AudioClipPianoKeySoundsPlayer audioClipPianoKeySoundsPlayer;
    protected static MockPianoKeySoundsPlayer mockPianoKeySoundsPlayer;
    protected static InMemoryAudioPerfectPitchSessionRepository inMemoryAudioPerfectPitchSessionRepository;
    protected static DomainEventsFactory domainEventsFactory;
    protected static EventPublisher eventPublisher;
    protected static EventBus eventBus;

    public static ApplicationContext context;

    private DI() {
    }

    public static void setContext(ApplicationContext context) {
        DI.context = context;
        initBeans();
    }

    private static void initBeans() {
        // TODO this cast seems awkward
        var genericContext = (GenericApplicationContext) context;

        genericContext.registerBean(AudioClipPianoKeySoundsPlayer.class);

        genericContext.registerBean(NotesNormalizingService.class);

        genericContext.registerBean(PianoKeyColorService.class);

        genericContext.registerBean(HintDemonstratorDelegator.class);

        genericContext.registerBean(SerializationCloner.class);

        genericContext.registerBean(PianoKeysFactory.class, () -> new PianoKeysFactory(genericContext.getBean(PianoKeyColorService.class)));

        // TODO pretty sure some suppliers are redundant
        genericContext.registerBean(PianoKeyboardAggregatesFactory.class, () -> new PianoKeyboardAggregatesFactory(genericContext.getBean(ObjectCloner.class), genericContext.getBean(PianoKeysFactory.class)));
        genericContext.registerBean(InMemoryPuzzleConfigPianoKeyboardRepository.class, () -> new InMemoryPuzzleConfigPianoKeyboardRepository(genericContext.getBean(PianoKeyboardAggregatesFactory.class)));

        genericContext.registerBean(InMemoryPuzzleConfigRepository.class, () -> new InMemoryPuzzleConfigRepository(genericContext.getBean(PuzzleConfigPianoKeyboardStorageAdapter.class), genericContext.getBean(AbstractPuzzleConfigAggregatesFactory.class)));

        genericContext.registerBean(InMemorySessionPianoKeyboardRepository.class, () -> new InMemorySessionPianoKeyboardRepository(genericContext.getBean(PianoKeyboardAggregatesFactory.class)));

        genericContext.registerBean(PuzzleConfigService.class, () -> new PuzzleConfigService(genericContext.getBean(PuzzleConfigRepository.class)));

        genericContext.registerBean(TestInMemoryAllPianoKeyboardRepository.class, () -> new TestInMemoryAllPianoKeyboardRepository(genericContext.getBean(PianoKeyboardAggregatesFactory.class)));

        genericContext.registerBean(AudioPerfectPitchSessionService.class, () -> new AudioPerfectPitchSessionService(genericContext.getBean(PuzzleConfigRepository.class), genericContext.getBean(AudioPerfectPitchSessionRepository.class), genericContext.getBean(SessionAggregatesFactory.class)));

        genericContext.registerBean(SolutionGeneratorsFactory.class);

        genericContext.registerBean(PuzzlesFactory.class, () -> new PuzzlesFactory(genericContext.getBean(SolutionGeneratorsFactory.class), genericContext.getBean(PuzzleConfigDTOAssembler.class)));

        genericContext.registerBean(SessionAggregatesFactory.class, () -> new SessionAggregatesFactory(genericContext.getBean(ObjectCloner.class), genericContext.getBean(PuzzleConfigRepository.class), genericContext.getBean(SessionPianoKeyboardStorageAdapter.class), genericContext.getBean(PuzzleConfigDTOAssembler.class), genericContext.getBean(PuzzlesFactory.class)));

        genericContext.registerBean(SessionRepositoryDelegator.class);

        genericContext.registerBean(PianoKeySoundFilesBuilder.class);

        genericContext.registerBean(PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator.class, () -> new PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator(genericContext.getBean(PianoKeySoundsPlayer.class)));

        genericContext.registerBean(InMemoryAudioPerfectPitchSessionRepository.class, () -> new InMemoryAudioPerfectPitchSessionRepository(genericContext.getBean(SessionAggregatesFactory.class), genericContext.getBean(SessionPianoKeyboardStorageAdapter.class)));

        genericContext.registerBean(EndSessionAggregateDTOAssemblersFactory.class);

        genericContext.registerBean(DomainEventsFactory.class);

        genericContext.registerBean(SpringEventPublisher.class, () -> new SpringEventPublisher((ApplicationEventPublisher) context));

        genericContext.registerBean(SpringEventBus.class);

        genericContext.registerBean(PuzzleConfigDTOAssembler.class, () -> new PuzzleConfigDTOAssembler(genericContext.getBean(PuzzleConfigRepository.class)));

        // javafx beans
        genericContext.registerBean(PuzzlePanesFactory.class, () -> new PuzzlePanesFactory(genericContext.getBean(SessionRepositoryDelegator.class)));
        genericContext.registerBean(StatsPanesFactory.class, () -> new StatsPanesFactory(genericContext.getBean(PuzzleConfigRepository.class), genericContext.getBean(SessionRepositoryDelegator.class)));

    }

    public static <T> T get(Class<T> someClass, Object... args) {
        // TODO refactoring
        try {
            var bean = args.length > 0 ? context.getBean(someClass, args) : context.getBean(someClass);
            return bean;
        } catch (BeansException e) {
            System.out.print("beans exception: " + e.getMessage());
        }

        var className = someClass.getName();
        // // TODO delete later
        // } else if (className == SoundHarmonicIntervalHintDemonstrator.class.getName()) {
        //     return (T) (env.equals(TEST_ENV) ? new FakeSoundHarmonicIntervalHintDemonstrator() : new AudioClipSoundHarmonicIntervalHintDemonstrator());

        if (className.equals(AudioPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (env.equals(TEST_ENV) ? new FakeAudioPerfectPitchSolutionGenerator() : new RandomAudioPerfectPitchSolutionGenerator((AudioPerfectPitchConfigDTO) args[0]));

        } else if (className.equals(VisualPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (env.equals(TEST_ENV) ? new FakeVisualPerfectPitchSolutionGenerator() : new RandomVisualPerfectPitchSolutionGenerator((VisualPerfectPitchConfigDTO) args[0]));

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

        } else {
            throw new IllegalArgumentException("DI dependency is not found: " + someClass.getName());
        }
    }

    // TODO wanna believe there's such a feature in SpringBoot. it's required for pure junit tests, so that each starts in the same DI-container state
    public static void deleteSingletons() {
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
