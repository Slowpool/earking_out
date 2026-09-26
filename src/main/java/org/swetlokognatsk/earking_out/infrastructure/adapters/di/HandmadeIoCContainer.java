package org.swetlokognatsk.earking_out.infrastructure.adapters.di;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeyboardHandlersRegister;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PuzzlePanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.StatsPanesFactory;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.ActualizePianoKeyboardsOnAudioPerfectPitchExercisePickedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintDemonstratingOnHintRepeatingRequestedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintDemonstratingOnNewPuzzleCreatedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.LogEventOnHintRepeatingRequestedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.LogEventOnNewPuzzleCreatedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.LogEventOnSessionFinishedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.LogEventOnSessionStartedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.LogEventOnUserTriedToGuessPuzzleHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.PuzzleConfigUpdatingOnPianoKeyPressedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.AudioPerfectPitchGuessingOnPianoKeyPressedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.SessionPianoKeyboardUpdatingOnSessionStartedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.SoundPlayerOnPianoKeyPressedHandler;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactoryResolver;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.VisualPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.EndSessionAggregateDTOAssemblersFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesParsingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.perfectpitch.EditableAudioPerfectPitchConfigValidator;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.perfectpitch.FinalizedAudioPerfectPitchConfigValidator;
import org.swetlokognatsk.earking_out.core.domain.services.domain.session.perfectpitch.PerfectPitchSessionStatsAggregator;
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
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundFilesResolver;
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
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.FakeVisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.RandomVisualPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.session.perfectpitch.InMemoryAudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.factories.puzzles.generators.SolutionGeneratorsFactory;
import org.swetlokognatsk.earking_out.infrastructure.sounds.PianoKeySoundFilesBuilder;

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
        Object dep/* endency */;
        if (someClass.equals(NotesNormalizingService.class)) {
            dep = new NotesNormalizingService();

        } else if (someClass.equals(HintDemonstrator.class)) {
            dep = get(HintDemonstratorDelegator.class);

        } else if (someClass.equals(HintDemonstratorDelegator.class)) {
            dep = new HintDemonstratorDelegator();

        } else if (someClass == SoundHarmonicIntervalHintDemonstrator.class) {
            dep = (DI.inTestMode() ? new FakeSoundHarmonicIntervalHintDemonstrator() : new AudioClipSoundHarmonicIntervalHintDemonstrator());

        } else if (someClass.equals(AudioPerfectPitchSolutionGenerator.class)) {
            dep = (DI.inTestMode() ? new FakeAudioPerfectPitchSolutionGenerator() : new RandomAudioPerfectPitchSolutionGenerator((AudioPerfectPitchConfigDTO) args[0]));

        } else if (someClass.equals(VisualPerfectPitchSolutionGenerator.class)) {
            dep = (DI.inTestMode() ? new FakeVisualPerfectPitchSolutionGenerator() : new RandomVisualPerfectPitchSolutionGenerator((VisualPerfectPitchConfigDTO) args[0]));

        } else if (someClass.equals(PianoKeyColorService.class)) {
            dep = new PianoKeyColorService();

        } else if (someClass.equals(PuzzleConfigService.class)) {
            dep = new PuzzleConfigService(get(PuzzleConfigRepository.class), get(PianoKeyboardRepository.class));

        } else if (someClass.equals(PianoKeyboardDtoAssembler.class)) {
            if (pianoKeyboardDtoAssembler == null) {
                pianoKeyboardDtoAssembler = new PianoKeyboardDtoAssembler();
            }
            dep = pianoKeyboardDtoAssembler;

        } else if (someClass.equals(PuzzleConfigRepository.class)) {
            dep = get(InMemoryPuzzleConfigRepository.class);

        } else if (someClass.equals(PuzzleConfigAggregatesFactoryResolver.class)) {
            dep = new PuzzleConfigAggregatesFactoryResolver();
        } else if (someClass.equals(AudioPerfectPitchConfigAggregatesFactory.class)) {
            dep = new AudioPerfectPitchConfigAggregatesFactory(get(ObjectCloner.class));
        } else if (someClass.equals(VisualPerfectPitchConfigAggregatesFactory.class)) {
            dep = new VisualPerfectPitchConfigAggregatesFactory(get(ObjectCloner.class));

        } else if (someClass.equals(InMemoryPuzzleConfigRepository.class)) {
            if (inMemoryPuzzleConfigRepository == null) {
                inMemoryPuzzleConfigRepository = new InMemoryPuzzleConfigRepository(get(PuzzleConfigAggregatesFactoryResolver.class), get(PuzzleConfigDTOAssembler.class));
            }
            dep = inMemoryPuzzleConfigRepository;

        } else if (someClass.equals(PianoKeyboardRepository.class)) {
            dep = get(InMemoryPianoKeyboardRepository.class);

        } else if (someClass.equals(InMemoryPianoKeyboardRepository.class)) {
            if (inMemoryPianoKeyboardRepository == null) {
                inMemoryPianoKeyboardRepository = new InMemoryPianoKeyboardRepository(get(PianoKeyboardAggregatesFactory.class), get(PianoKeyboardDtoAssembler.class));
            }
            dep = inMemoryPianoKeyboardRepository;

        } else if (someClass.equals(AudioPerfectPitchSessionService.class)) {
            dep = new AudioPerfectPitchSessionService(get(PuzzleConfigRepository.class), get(AudioPerfectPitchSessionRepository.class), get(SessionAggregatesFactory.class), get(FinalizedAudioPerfectPitchConfigValidator.class));

        } else if (someClass.equals(PianoKeyboardService.class)) {
            dep = new PianoKeyboardService(get(PianoKeyboardRepository.class), get(AudioPerfectPitchSessionRepository.class));

        } else if (someClass.equals(PianoKeyboardAggregatesFactory.class)) {
            if (pianoKeyboardAggregatesFactory == null) {
                pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory(get(ObjectCloner.class), get(PianoKeysFactory.class));
            }
            dep = pianoKeyboardAggregatesFactory;

        } else if (someClass.equals(PianoKeysFactory.class)) {
            dep = new PianoKeysFactory(get(PianoKeyColorService.class));

        } else if (someClass.equals(ObjectCloner.class)) {
            dep = get(SerializationCloner.class);

        } else if (someClass.equals(SerializationCloner.class)) {
            dep = new SerializationCloner();

        } else if (someClass.equals(PuzzlesFactory.class)) {
            dep = new PuzzlesFactory(get(SolutionGeneratorsFactory.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(SolutionGeneratorsFactory.class)) {
            dep = new SolutionGeneratorsFactory();

        } else if (someClass.equals(PuzzleConfigDTOAssembler.class)) {
            dep = new PuzzleConfigDTOAssembler();

        } else if (someClass.equals(SessionAggregatesFactory.class)) {
            dep = new SessionAggregatesFactory(get(ObjectCloner.class), get(PuzzleConfigRepository.class), get(PianoKeyboardRepository.class), get(PuzzleConfigDTOAssembler.class), get(PuzzlesFactory.class));

        } else if (someClass.equals(PuzzlePanesFactory.class)) {
            dep = new PuzzlePanesFactory(get(SessionRepositoryDelegator.class), get(PianoKeyboardHandlersRegister.class), get(PianoKeyboardService.class));

        } else if (someClass.equals(HintDemonstrator.class)) {
            dep = new HintDemonstratorDelegator();

        } else if (someClass.equals(PianoKeySoundsPlayer.class)) {
            dep = (DI.inTestMode() ? get(MockPianoKeySoundsPlayer.class) : get(AudioClipPianoKeySoundsPlayer.class));

        } else if (someClass.equals(MockPianoKeySoundsPlayer.class)) {
            if (mockPianoKeySoundsPlayer == null) {
                mockPianoKeySoundsPlayer = new MockPianoKeySoundsPlayer();
            }
            dep = mockPianoKeySoundsPlayer;

        } else if (someClass.equals(AudioClipPianoKeySoundsPlayer.class)) {
            if (audioClipPianoKeySoundsPlayer == null) {
                audioClipPianoKeySoundsPlayer = new AudioClipPianoKeySoundsPlayer();
            }
            dep = audioClipPianoKeySoundsPlayer;

        } else if (someClass.equals(PianoKeySoundFilesBuilder.class)) {
            dep = new PianoKeySoundFilesBuilder(get(PianoKeySoundFilesResolver.class));

        } else if (someClass.equals(AudioPerfectPitchHintDemonstrator.class)) {
            dep = get(PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator.class);

        } else if (someClass.equals(PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator.class)) {
            dep = new PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator(get(PianoKeySoundsPlayer.class));

        } else if (someClass.equals(AudioPerfectPitchSessionRepository.class)) {
            dep = get(InMemoryAudioPerfectPitchSessionRepository.class);

        } else if (someClass.equals(InMemoryAudioPerfectPitchSessionRepository.class)) {
            if (inMemoryAudioPerfectPitchSessionRepository == null) {
                inMemoryAudioPerfectPitchSessionRepository = new InMemoryAudioPerfectPitchSessionRepository(get(SessionAggregatesFactory.class));
            }
            dep = inMemoryAudioPerfectPitchSessionRepository;

        } else if (someClass.equals(SessionRepositoryDelegator.class)) {
            dep = new SessionRepositoryDelegator();

        } else if (someClass.equals(EndSessionAggregateDTOAssemblersFactory.class)) {
            dep = new EndSessionAggregateDTOAssemblersFactory();

        } else if (someClass.equals(StatsPanesFactory.class)) {
            dep = new StatsPanesFactory(get(PuzzleConfigRepository.class), get(SessionRepositoryDelegator.class));

        } else if (someClass.equals(DomainEventsFactory.class)) {
            if (domainEventsFactory == null) {
                domainEventsFactory = new DomainEventsFactory();
            }
            dep = domainEventsFactory;

        } else if (someClass.equals(EventPublisher.class)) {
            dep = get(GreenrobotEventBus.class);

        } else if (someClass.equals(EventBus.class)) {
            dep = get(GreenrobotEventBus.class);

        } else if (someClass.equals(DomainEventJsonSerializer.class)) {
            dep = get(JacksonJsonSerializer.class);

        } else if (someClass.equals(JacksonJsonSerializer.class)) {
            if (jacksonDomainEventJsonSerializer == null) {
                jacksonDomainEventJsonSerializer = new JacksonJsonSerializer();
            }
            dep = jacksonDomainEventJsonSerializer;

        } else if (someClass.equals(EventStore.class)) {
            dep = get(InMemoryEventStore.class);

        } else if (someClass.equals(InMemoryEventStore.class)) {
            if (inMemoryEventStore == null) {
                inMemoryEventStore = new InMemoryEventStore(get(DomainEventJsonSerializer.class));
            }
            dep = inMemoryEventStore;

        } else if (someClass.equals(GreenrobotEventBus.class)) {
            if (greenrobotEventBus == null) {
                greenrobotEventBus = new GreenrobotEventBus(org.greenrobot.eventbus.EventBus.getDefault());
            }
            dep = greenrobotEventBus;

        } else if (someClass.equals(SoundPlayerOnPianoKeyPressedHandler.class)) {
            dep = new SoundPlayerOnPianoKeyPressedHandler(get(PianoKeySoundsPlayer.class), get(PianoKeyboardRepository.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(HintDemonstratingOnNewPuzzleCreatedHandler.class)) {
            dep = new HintDemonstratingOnNewPuzzleCreatedHandler(get(HintDemonstratorDelegator.class));

        } else if (someClass.equals(HintDemonstratingOnHintRepeatingRequestedHandler.class)) {
            dep = new HintDemonstratingOnHintRepeatingRequestedHandler(get(HintDemonstratorDelegator.class));

        } else if (someClass.equals(PuzzleConfigUpdatingOnPianoKeyPressedHandler.class)) {
            dep = new PuzzleConfigUpdatingOnPianoKeyPressedHandler(get(PuzzleConfigService.class));

        } else if (someClass.equals(SessionPianoKeyboardUpdatingOnSessionStartedHandler.class)) {
            dep = new SessionPianoKeyboardUpdatingOnSessionStartedHandler(get(PianoKeyboardService.class));

        } else if (someClass.equals(AudioPerfectPitchGuessingOnPianoKeyPressedHandler.class)) {
            dep = new AudioPerfectPitchGuessingOnPianoKeyPressedHandler(get(AudioPerfectPitchSessionService.class));

        } else if (someClass.equals(LogEventOnSessionStartedHandler.class)) {
            dep = new LogEventOnSessionStartedHandler(get(EventStore.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(LogEventOnNewPuzzleCreatedHandler.class)) {
            dep = new LogEventOnNewPuzzleCreatedHandler(get(EventStore.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(LogEventOnUserTriedToGuessPuzzleHandler.class)) {
            dep = new LogEventOnUserTriedToGuessPuzzleHandler(get(EventStore.class), get(PuzzleConfigRepository.class), get(SessionRepositoryDelegator.class));

        } else if (someClass.equals(LogEventOnHintRepeatingRequestedHandler.class)) {
            dep = new LogEventOnHintRepeatingRequestedHandler(get(EventStore.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(LogEventOnSessionFinishedHandler.class)) {
            dep = new LogEventOnSessionFinishedHandler(get(EventStore.class), get(PuzzleConfigRepository.class), get(SessionRepositoryDelegator.class));

        } else if (someClass.equals(ActualizePianoKeyboardsOnAudioPerfectPitchExercisePickedHandler.class)) {
            dep = new ActualizePianoKeyboardsOnAudioPerfectPitchExercisePickedHandler(get(PianoKeyboardRepository.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(EditableAudioPerfectPitchConfigValidator.class)) {
            dep = new EditableAudioPerfectPitchConfigValidator();

        } else if (someClass.equals(FinalizedAudioPerfectPitchConfigValidator.class)) {
            dep = new FinalizedAudioPerfectPitchConfigValidator();

        } else if (someClass.equals(NotesParsingService.class)) {
            dep = new NotesParsingService();

        } else if (someClass.equals(PerfectPitchSessionStatsAggregator.class)) {
            dep = new PerfectPitchSessionStatsAggregator<PerfectPitchExercise>();

        } else {
            throw new IllegalArgumentException("DI dependency is not found: " + someClass.getName());
        }
        return (T) dep;
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
