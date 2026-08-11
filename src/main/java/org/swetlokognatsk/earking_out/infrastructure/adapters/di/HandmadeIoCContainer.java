package org.swetlokognatsk.earking_out.infrastructure.adapters.di;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeyboardHandlersRegister;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PuzzlePanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.StatsPanesFactory;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintDemonstratingOnHintRepeatingRequestedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintDemonstratingOnNewPuzzleCreatedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.PuzzleConfigUpdatingOnPianoKeyPressedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.SessionEventsLoggerHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.SessionGuessingOnPianoKeyPressedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.SessionPianoKeyboardUpdatingOnSessionStartedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.SoundPlayerOnPianoKeyPressedHandler;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.EndSessionAggregateDTOAssemblersFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.IoCContainer;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.sound.AudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.sound.SoundHarmonicIntervalHintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.SerializationCloner;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.JacksonJsonSerializer;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot.GreenrobotEventBus;
import org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing.InMemoryEventStore;
import org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing.SQLiteEventStore;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.HintDemonstratorDelegator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.AudioClipSoundHarmonicIntervalHintDemonstrator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.FakeSoundHarmonicIntervalHintDemonstrator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.AudioClipPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.InMemoryPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.MockPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.PianoKeySoundFilesBuilder;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.FakeVisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.RandomVisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.session.perfectpitch.InMemoryAudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.factories.puzzles.generators.SolutionGeneratorsFactory;

public final class HandmadeIoCContainer implements IoCContainer {

    private Map<Class<?>, ?> singletons;
    // singleton lifetime simulation
    private static InMemoryPuzzleConfigRepository inMemoryPuzzleConfigRepository;
    private static PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory;
    private static AudioClipPianoKeySoundsPlayer audioClipPianoKeySoundsPlayer;
    private static MockPianoKeySoundsPlayer mockPianoKeySoundsPlayer;
    private static InMemoryAudioPerfectPitchSessionRepository inMemoryAudioPerfectPitchSessionRepository;
    private static DomainEventsFactory domainEventsFactory;
    private static EventPublisher eventPublisher;
    private static InMemoryPianoKeyboardRepository inMemoryPianoKeyboardRepository;
    private static GreenrobotEventBus greenrobotEventBus;
    private static InMemoryEventStore inMemoryEventStore;
    private static JacksonJsonSerializer jacksonDomainEventJsonSerializer;
    private static PianoKeyboardDtoAssembler pianoKeyboardDtoAssembler;

    // // TODO remove or finish
    // private <O extends Object> O getSingleton(Class<O> someClass, Object[] args) {
    //     var singleton = singletons.get(someClass);
    //     if (singleton == null) {
    //         singletons = createSingleton(someClass, args)
    //     }
    // }

    // private <O extends Object> O getSingleton(Class<O> someClass, Object[] args) {

    // }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> someClass, Object... args) {

        var className = someClass.getName();
        if (className.equals(NotesNormalizingService.class.getName())) {
            return (T) new NotesNormalizingService();

        } else if (className.equals(HintDemonstrator.class.getName())) {
            return (T) get(HintDemonstratorDelegator.class);

        } else if (className.equals(HintDemonstratorDelegator.class.getName())) {
            return (T) new HintDemonstratorDelegator();

        } else if (className == SoundHarmonicIntervalHintDemonstrator.class.getName()) {
            return (T) (DI.inTestMode() ? new FakeSoundHarmonicIntervalHintDemonstrator() : new AudioClipSoundHarmonicIntervalHintDemonstrator());

        } else if (className.equals(AudioPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (DI.inTestMode() ? new FakeAudioPerfectPitchSolutionGenerator() : new RandomAudioPerfectPitchSolutionGenerator((AudioPerfectPitchConfigDTO) args[0]));

        } else if (className.equals(VisualPerfectPitchSolutionGenerator.class.getName())) {
            return (T) (DI.inTestMode() ? new FakeVisualPerfectPitchSolutionGenerator() : new RandomVisualPerfectPitchSolutionGenerator((VisualPerfectPitchConfigDTO) args[0]));

        } else if (className.equals(PianoKeyColorService.class.getName())) {
            return (T) new PianoKeyColorService();

        } else if (className.equals(PuzzleConfigService.class.getName())) {
            return (T) new PuzzleConfigService(get(PuzzleConfigRepository.class), get(PianoKeyboardRepository.class));

        } else if (className.equals(PianoKeyboardDtoAssembler.class.getName())) {
            if (pianoKeyboardDtoAssembler == null) {
                pianoKeyboardDtoAssembler = new PianoKeyboardDtoAssembler();
            }
            return (T) pianoKeyboardDtoAssembler;

        } else if (className.equals(PuzzleConfigRepository.class.getName())) {
            return (T) get(InMemoryPuzzleConfigRepository.class);

        } else if (className.equals(AbstractPuzzleConfigAggregatesFactory.class.getName())) {
            return (T) new AbstractPuzzleConfigAggregatesFactory(get(ObjectCloner.class));

        } else if (className.equals(InMemoryPuzzleConfigRepository.class.getName())) {
            if (inMemoryPuzzleConfigRepository == null) {
                inMemoryPuzzleConfigRepository = new InMemoryPuzzleConfigRepository(get(AbstractPuzzleConfigAggregatesFactory.class));
            }
            return (T) inMemoryPuzzleConfigRepository;

        } else if (className.equals(PianoKeyboardRepository.class.getName())) {
            return (T) get(InMemoryPianoKeyboardRepository.class);

        } else if (className.equals(InMemoryPianoKeyboardRepository.class.getName())) {
            if (inMemoryPianoKeyboardRepository == null) {
                inMemoryPianoKeyboardRepository = new InMemoryPianoKeyboardRepository(get(PianoKeyboardAggregatesFactory.class), get(PianoKeyboardDtoAssembler.class));
            }
            return (T) inMemoryPianoKeyboardRepository;

        } else if (className.equals(AudioPerfectPitchSessionService.class.getName())) {
            return (T) new AudioPerfectPitchSessionService(get(PuzzleConfigRepository.class), get(AudioPerfectPitchSessionRepository.class), get(SessionAggregatesFactory.class));

        } else if (className.equals(PianoKeyboardService.class.getName())) {
            return (T) new PianoKeyboardService(get(PianoKeyboardRepository.class));

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
            return (T) new SessionAggregatesFactory(get(ObjectCloner.class), get(PuzzleConfigRepository.class), get(PianoKeyboardRepository.class), get(PuzzleConfigDTOAssembler.class), get(PuzzlesFactory.class));

        } else if (className.equals(PuzzlePanesFactory.class.getName())) {
            return (T) new PuzzlePanesFactory(get(SessionRepositoryDelegator.class), get(PianoKeyboardHandlersRegister.class), get(PianoKeyboardService.class));

        } else if (className.equals(HintDemonstrator.class.getName())) {
            return (T) new HintDemonstratorDelegator();

        } else if (className.equals(PianoKeySoundsPlayer.class.getName())) {
            return (T) (DI.inTestMode() ? get(MockPianoKeySoundsPlayer.class) : get(AudioClipPianoKeySoundsPlayer.class));

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
                inMemoryAudioPerfectPitchSessionRepository = new InMemoryAudioPerfectPitchSessionRepository(get(SessionAggregatesFactory.class));
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
            return (T) get(GreenrobotEventBus.class);

        } else if (className.equals(EventBus.class.getName())) {
            return (T) get(GreenrobotEventBus.class);

        } else if (className.equals(DomainEventJsonSerializer.class.getName())) {
            return (T) get(JacksonJsonSerializer.class);

        } else if (className.equals(JacksonJsonSerializer.class.getName())) {
            if (jacksonDomainEventJsonSerializer == null) {
                jacksonDomainEventJsonSerializer = new JacksonJsonSerializer();
            }
            return (T) jacksonDomainEventJsonSerializer;

        } else if (className.equals(EventStore.class.getName())) {
            return (T) get(InMemoryEventStore.class);

        } else if (className.equals(InMemoryEventStore.class.getName())) {
            if (inMemoryEventStore == null) {
                inMemoryEventStore = new InMemoryEventStore(get(DomainEventJsonSerializer.class));
            }
            return (T) inMemoryEventStore;

        } else if (className.equals(GreenrobotEventBus.class.getName())) {
            if (greenrobotEventBus == null) {
                greenrobotEventBus = new GreenrobotEventBus(org.greenrobot.eventbus.EventBus.getDefault());
            }
            return (T) greenrobotEventBus;

        } else if (className.equals(SoundPlayerOnPianoKeyPressedHandler.class.getName())) {
            return (T) new SoundPlayerOnPianoKeyPressedHandler(get(PianoKeySoundsPlayer.class), get(PianoKeyboardRepository.class), get(PuzzleConfigRepository.class));

        } else if (className.equals(HintDemonstratingOnNewPuzzleCreatedHandler.class.getName())) {
            return (T) new HintDemonstratingOnNewPuzzleCreatedHandler(get(HintDemonstratorDelegator.class));

        } else if (className.equals(HintDemonstratingOnHintRepeatingRequestedHandler.class.getName())) {
            return (T) new HintDemonstratingOnHintRepeatingRequestedHandler(get(HintDemonstratorDelegator.class));

        } else if (className.equals(SessionEventsLoggerHandler.class.getName())) {
            return (T) new SessionEventsLoggerHandler(get(EventStore.class));

        } else if (className.equals(PuzzleConfigUpdatingOnPianoKeyPressedHandler.class.getName())) {
            return (T) new PuzzleConfigUpdatingOnPianoKeyPressedHandler(get(PuzzleConfigService.class));

        } else if (className.equals(SessionPianoKeyboardUpdatingOnSessionStartedHandler.class.getName())) {
            return (T) new SessionPianoKeyboardUpdatingOnSessionStartedHandler(get(PianoKeyboardService.class));

        } else if (className.equals(SessionGuessingOnPianoKeyPressedHandler.class.getName())) {
            // TODO this logic must be in factory
            var sessionServices = new HashMap<Exercise, SessionService<?, ?, ?>>();
            sessionServices.put(new AudioPerfectPitchExercise(), get(AudioPerfectPitchSessionService.class));
            return (T) new SessionGuessingOnPianoKeyPressedHandler(sessionServices);

        } else {
            throw new IllegalArgumentException("DI dependency is not found: " + someClass.getName());
        }
    }

    public void refreshDependencies() {
        inMemoryPuzzleConfigRepository = null;
        pianoKeyboardAggregatesFactory = null;
        audioClipPianoKeySoundsPlayer = null;
        mockPianoKeySoundsPlayer = null;
        inMemoryAudioPerfectPitchSessionRepository = null;
        domainEventsFactory = null;
        eventPublisher = null;
        inMemoryPianoKeyboardRepository = null;
        greenrobotEventBus = null;
        inMemoryEventStore = null;
        jacksonDomainEventJsonSerializer = null;
        pianoKeyboardDtoAssembler = null;
    }

}
