package org.swetlokognatsk.earking_out.inftrastructure.adapters.di;

import java.util.Map;
import org.springframework.context.ApplicationEventPublisher;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PuzzlePanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.StatsPanesFactory;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
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
import org.swetlokognatsk.earking_out.core.ports.di.CustomDI;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
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
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.sound.AudioClipSoundHarmonicIntervalHintDemonstrator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.sound.FakeSoundHarmonicIntervalHintDemonstrator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.sound.PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator;
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

public final class HandmadeDI implements CustomDI {

    protected Map<Class<?>, ?> singletons;
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
    protected static InMemoryPuzzleConfigPianoKeyboardRepository inMemoryPuzzleConfigPianoKeyboardRepository;

    // // TODO remove or finish
    // private <O extends Object> O getSingleton(Class<O> someClass, Object[] args) {
    //     var singleton = singletons.get(someClass);
    //     if (singleton == null) {
    //         singletons = createSingleton(someClass, args)
    //     }
    // }

    // private <O extends Object> O getSingleton(Class<O> someClass, Object[] args) {

    // }

    public <T> T get(Class<T> someClass, Object... args) {

        var className = someClass.getName();
        if (className.equals(NotesNormalizingService.class.getName())) {
            return (T) new NotesNormalizingService();

        } else if (className.equals(HintDemonstrator.class.getName())) {
            return (T) new HintDemonstratorDelegator();
        } else if (className == SoundHarmonicIntervalHintDemonstrator.class.getName()) {
            return (T) (DI.isTestEnv() ? new FakeSoundHarmonicIntervalHintDemonstrator() : new AudioClipSoundHarmonicIntervalHintDemonstrator());

        } else if (className.equals(AudioPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (DI.isTestEnv() ? new FakeAudioPerfectPitchSolutionGenerator() : new RandomAudioPerfectPitchSolutionGenerator((AudioPerfectPitchConfigDTO) args[0]));

        } else if (className.equals(VisualPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (DI.isTestEnv() ? new FakeVisualPerfectPitchSolutionGenerator() : new RandomVisualPerfectPitchSolutionGenerator((VisualPerfectPitchConfigDTO) args[0]));

        } else if (className.equals(PianoKeyColorService.class.getName())) {
            return (T) new PianoKeyColorService();

        } else if (className.equals(PuzzleConfigService.class.getName())) {
            return (T) new PuzzleConfigService(get(PuzzleConfigRepository.class));

        } else if (className.equals(PuzzleConfigRepository.class.getName())) {
            return (T) get(InMemoryPuzzleConfigRepository.class);

        } else if (className.equals(AbstractPuzzleConfigAggregatesFactory.class.getName())) {
            return (T) new AbstractPuzzleConfigAggregatesFactory(get(ObjectCloner.class));

        } else if (className.equals(InMemoryPuzzleConfigRepository.class.getName())) {
            if (inMemoryPuzzleConfigRepository == null) {
                inMemoryPuzzleConfigRepository = new InMemoryPuzzleConfigRepository(get(PuzzleConfigPianoKeyboardStorageAdapter.class), get(AbstractPuzzleConfigAggregatesFactory.class));
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
                pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory(get(ObjectCloner.class), get(PianoKeysFactory.class));
            }
            return (T) pianoKeyboardAggregatesFactory;

        } else if (className.equals(PianoKeysFactory.class.getName())) {
            return (T) new PianoKeysFactory(get(PianoKeyColorService.class));

        } else if (className.equals(ObjectCloner.class.getName())) {
            return (T) get(SerializationCloner.class);

        } else if (className.equals(SerializationCloner.class.getName())) {
            return (T) new SerializationCloner();

        } else if (className.equals(PuzzlesFactory.class.getName())) {
            return (T) new PuzzlesFactory(get(SolutionGeneratorsFactory.class), get(PuzzleConfigDTOAssembler.class));

        } else if (className.equals(SolutionGeneratorsFactory.class.getName())) {
            return (T) new SolutionGeneratorsFactory();

        } else if (className.equals(PuzzleConfigDTOAssembler.class.getName())) {
            return (T) new PuzzleConfigDTOAssembler(get(PuzzleConfigRepository.class));

        } else if (className.equals(SessionAggregatesFactory.class.getName())) {
            return (T) new SessionAggregatesFactory(get(ObjectCloner.class), get(PuzzleConfigRepository.class), get(SessionPianoKeyboardStorageAdapter.class), get(PuzzleConfigDTOAssembler.class), get(PuzzlesFactory.class));

        } else if (className.equals(PuzzlePanesFactory.class.getName())) {
            return (T) new PuzzlePanesFactory(get(SessionRepositoryDelegator.class));

        } else if (className.equals(HintDemonstrator.class.getName())) {
            return (T) new HintDemonstratorDelegator();

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

        } else if (className.equals(DomainEventsFactory.class.getName())) {
            if (domainEventsFactory == null) {
                domainEventsFactory = new DomainEventsFactory();
            }
            return (T) domainEventsFactory;

        } else if (className.equals(EventPublisher.class.getName())) {
            if (eventPublisher == null) {
                eventPublisher = get(SpringEventPublisher.class);
            }
            return (T) eventPublisher;

            // TODO use lightweight event publisher
        } else if (className.equals(SpringEventPublisher.class.getName())) {
            // return (T) new SpringEventPublisher((ApplicationEventPublisher) context);
            return (T) null;

        } else if (className.equals(EventBus.class.getName())) {
            if (eventBus == null) {
                eventBus = get(SpringEventBus.class);
            }
            return (T) eventBus;

        } else if (className.equals(SpringEventBus.class.getName())) {
            return (T) new SpringEventBus();

        } else {
            throw new IllegalArgumentException("DI dependency is not found: " + someClass.getName());
        }
    }

    public void refreshDependencies() {
        inMemoryPuzzleConfigRepository = null;
        pianoKeyboardAggregatesFactory = null;
        inMemoryPuzzleConfigPianoKeyboardRepository = null;
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
