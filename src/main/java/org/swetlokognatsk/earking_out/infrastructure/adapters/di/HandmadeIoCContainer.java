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
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.EndSessionDTOAssemblersFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.SessionService;
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

        Object impl;
        if (someClass.equals(NotesNormalizingService.class)) {
            impl = new NotesNormalizingService();

        } else if (someClass.equals(HintDemonstrator.class)) {
            impl = get(HintDemonstratorDelegator.class);

        } else if (someClass.equals(HintDemonstratorDelegator.class)) {
            impl = new HintDemonstratorDelegator();

        } else if (someClass == SoundHarmonicIntervalHintDemonstrator.class) {
            impl = (DI.inTestMode() ? new FakeSoundHarmonicIntervalHintDemonstrator() : new AudioClipSoundHarmonicIntervalHintDemonstrator());

        } else if (someClass.equals(AudioPerfectPitchSolutionGenerator.class)) {
            impl = (DI.inTestMode() ? new FakeAudioPerfectPitchSolutionGenerator() : new RandomAudioPerfectPitchSolutionGenerator((AudioPerfectPitchConfigDTO) args[0]));

        } else if (someClass.equals(VisualPerfectPitchSolutionGenerator.class)) {
            impl = (DI.inTestMode() ? new FakeVisualPerfectPitchSolutionGenerator() : new RandomVisualPerfectPitchSolutionGenerator((VisualPerfectPitchConfigDTO) args[0]));

        } else if (someClass.equals(PianoKeyColorService.class)) {
            impl = new PianoKeyColorService();

        } else if (someClass.equals(PuzzleConfigService.class)) {
            impl = new PuzzleConfigService(get(PuzzleConfigRepository.class), get(PianoKeyboardRepository.class));

        } else if (someClass.equals(PianoKeyboardDtoAssembler.class)) {
            if (pianoKeyboardDtoAssembler == null) {
                pianoKeyboardDtoAssembler = new PianoKeyboardDtoAssembler();
            }
            impl = pianoKeyboardDtoAssembler;

        } else if (someClass.equals(PuzzleConfigRepository.class)) {
            impl = get(InMemoryPuzzleConfigRepository.class);

        } else if (someClass.equals(AbstractPuzzleConfigAggregatesFactory.class)) {
            impl = new AbstractPuzzleConfigAggregatesFactory(get(ObjectCloner.class));

        } else if (someClass.equals(InMemoryPuzzleConfigRepository.class)) {
            if (inMemoryPuzzleConfigRepository == null) {
                inMemoryPuzzleConfigRepository = new InMemoryPuzzleConfigRepository(get(AbstractPuzzleConfigAggregatesFactory.class), get(PuzzleConfigDTOAssembler.class));
            }
            impl = inMemoryPuzzleConfigRepository;

        } else if (someClass.equals(PianoKeyboardRepository.class)) {
            impl = get(InMemoryPianoKeyboardRepository.class);

        } else if (someClass.equals(InMemoryPianoKeyboardRepository.class)) {
            if (inMemoryPianoKeyboardRepository == null) {
                inMemoryPianoKeyboardRepository = new InMemoryPianoKeyboardRepository(get(PianoKeyboardAggregatesFactory.class), get(PianoKeyboardDtoAssembler.class));
            }
            impl = inMemoryPianoKeyboardRepository;

        } else if (someClass.equals(AudioPerfectPitchSessionService.class)) {
            impl = new AudioPerfectPitchSessionService(get(PuzzleConfigRepository.class), get(AudioPerfectPitchSessionRepository.class), get(SessionAggregatesFactory.class));

        } else if (someClass.equals(PianoKeyboardService.class)) {
            impl = new PianoKeyboardService(get(PianoKeyboardRepository.class), get(AudioPerfectPitchSessionRepository.class));

        } else if (someClass.equals(PianoKeyboardAggregatesFactory.class)) {
            if (pianoKeyboardAggregatesFactory == null) {
                pianoKeyboardAggregatesFactory = new PianoKeyboardAggregatesFactory(get(ObjectCloner.class), get(PianoKeysFactory.class));
            }
            impl = pianoKeyboardAggregatesFactory;

        } else if (someClass.equals(PianoKeysFactory.class)) {
            impl = new PianoKeysFactory(get(PianoKeyColorService.class));

        } else if (someClass.equals(ObjectCloner.class)) {
            impl = get(SerializationCloner.class);

        } else if (someClass.equals(SerializationCloner.class)) {
            impl = new SerializationCloner();

        } else if (someClass.equals(PuzzlesFactory.class)) {
            impl = new PuzzlesFactory(get(SolutionGeneratorsFactory.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(SolutionGeneratorsFactory.class)) {
            impl = new SolutionGeneratorsFactory();

        } else if (someClass.equals(PuzzleConfigDTOAssembler.class)) {
            impl = new PuzzleConfigDTOAssembler();

        } else if (someClass.equals(SessionAggregatesFactory.class)) {
            impl = new SessionAggregatesFactory(get(ObjectCloner.class), get(PuzzleConfigRepository.class), get(PianoKeyboardRepository.class), get(PuzzleConfigDTOAssembler.class), get(PuzzlesFactory.class));

        } else if (someClass.equals(PuzzlePanesFactory.class)) {
            impl = new PuzzlePanesFactory(get(SessionRepositoryDelegator.class), get(PianoKeyboardHandlersRegister.class), get(PianoKeyboardService.class));

        } else if (someClass.equals(HintDemonstrator.class)) {
            impl = new HintDemonstratorDelegator();

        } else if (someClass.equals(PianoKeySoundsPlayer.class)) {
            impl = (DI.inTestMode() ? get(MockPianoKeySoundsPlayer.class) : get(AudioClipPianoKeySoundsPlayer.class));

        } else if (someClass.equals(MockPianoKeySoundsPlayer.class)) {
            if (mockPianoKeySoundsPlayer == null) {
                mockPianoKeySoundsPlayer = new MockPianoKeySoundsPlayer();
            }
            impl = mockPianoKeySoundsPlayer;

        } else if (someClass.equals(AudioClipPianoKeySoundsPlayer.class)) {
            if (audioClipPianoKeySoundsPlayer == null) {
                audioClipPianoKeySoundsPlayer = new AudioClipPianoKeySoundsPlayer();
            }
            impl = audioClipPianoKeySoundsPlayer;

        } else if (someClass.equals(PianoKeySoundFilesBuilder.class)) {
            impl = new PianoKeySoundFilesBuilder();

        } else if (someClass.equals(AudioPerfectPitchHintDemonstrator.class)) {
            impl = get(PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator.class);

        } else if (someClass.equals(PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator.class)) {
            impl = new PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator(get(PianoKeySoundsPlayer.class));

        } else if (someClass.equals(AudioPerfectPitchSessionRepository.class)) {
            impl = get(InMemoryAudioPerfectPitchSessionRepository.class);

        } else if (someClass.equals(InMemoryAudioPerfectPitchSessionRepository.class)) {
            if (inMemoryAudioPerfectPitchSessionRepository == null) {
                inMemoryAudioPerfectPitchSessionRepository = new InMemoryAudioPerfectPitchSessionRepository(get(SessionAggregatesFactory.class));
            }
            impl = inMemoryAudioPerfectPitchSessionRepository;

        } else if (someClass.equals(SessionRepositoryDelegator.class)) {
            impl = new SessionRepositoryDelegator();

        } else if (someClass.equals(EndSessionDTOAssemblersFactory.class)) {
            impl = new EndSessionDTOAssemblersFactory();

        } else if (someClass.equals(StatsPanesFactory.class)) {
            impl = new StatsPanesFactory(get(PuzzleConfigRepository.class), get(SessionRepositoryDelegator.class));

        } else if (someClass.equals(DomainEventsFactory.class)) {
            if (domainEventsFactory == null) {
                domainEventsFactory = new DomainEventsFactory();
            }
            impl = domainEventsFactory;

        } else if (someClass.equals(EventPublisher.class)) {
            impl = get(GreenrobotEventBus.class);

        } else if (someClass.equals(EventBus.class)) {
            impl = get(GreenrobotEventBus.class);

        } else if (someClass.equals(DomainEventJsonSerializer.class)) {
            impl = get(JacksonJsonSerializer.class);

        } else if (someClass.equals(JacksonJsonSerializer.class)) {
            if (jacksonDomainEventJsonSerializer == null) {
                jacksonDomainEventJsonSerializer = new JacksonJsonSerializer();
            }
            impl = jacksonDomainEventJsonSerializer;

        } else if (someClass.equals(EventStore.class)) {
            impl = get(InMemoryEventStore.class);

        } else if (someClass.equals(InMemoryEventStore.class)) {
            if (inMemoryEventStore == null) {
                inMemoryEventStore = new InMemoryEventStore(get(DomainEventJsonSerializer.class));
            }
            impl = inMemoryEventStore;

        } else if (someClass.equals(GreenrobotEventBus.class)) {
            if (greenrobotEventBus == null) {
                greenrobotEventBus = new GreenrobotEventBus(org.greenrobot.eventbus.EventBus.getDefault());
            }
            impl = greenrobotEventBus;

        } else if (someClass.equals(SoundPlayerOnPianoKeyPressedHandler.class)) {
            impl = new SoundPlayerOnPianoKeyPressedHandler(get(PianoKeySoundsPlayer.class), get(PianoKeyboardRepository.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(HintDemonstratingOnNewPuzzleCreatedHandler.class)) {
            impl = new HintDemonstratingOnNewPuzzleCreatedHandler(get(HintDemonstratorDelegator.class));

        } else if (someClass.equals(HintDemonstratingOnHintRepeatingRequestedHandler.class)) {
            impl = new HintDemonstratingOnHintRepeatingRequestedHandler(get(HintDemonstratorDelegator.class));

        } else if (someClass.equals(PuzzleConfigUpdatingOnPianoKeyPressedHandler.class)) {
            impl = new PuzzleConfigUpdatingOnPianoKeyPressedHandler(get(PuzzleConfigService.class));

        } else if (someClass.equals(SessionPianoKeyboardUpdatingOnSessionStartedHandler.class)) {
            impl = new SessionPianoKeyboardUpdatingOnSessionStartedHandler(get(PianoKeyboardService.class));

        } else if (someClass.equals(AudioPerfectPitchGuessingOnPianoKeyPressedHandler.class)) {
            impl = new AudioPerfectPitchGuessingOnPianoKeyPressedHandler(get(AudioPerfectPitchSessionService.class));

        } else if (someClass.equals(LogEventOnSessionStartedHandler.class)) {
            impl = new LogEventOnSessionStartedHandler(get(EventStore.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(LogEventOnNewPuzzleCreatedHandler.class)) {
            impl = new LogEventOnNewPuzzleCreatedHandler(get(EventStore.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(LogEventOnUserTriedToGuessPuzzleHandler.class)) {
            impl = new LogEventOnUserTriedToGuessPuzzleHandler(get(EventStore.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(LogEventOnHintRepeatingRequestedHandler.class)) {
            impl = new LogEventOnHintRepeatingRequestedHandler(get(EventStore.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(LogEventOnSessionFinishedHandler.class)) {
            impl = new LogEventOnSessionFinishedHandler(get(EventStore.class), get(PuzzleConfigRepository.class));

        } else if (someClass.equals(ActualizePianoKeyboardsOnAudioPerfectPitchExercisePickedHandler.class)) {
            impl = new ActualizePianoKeyboardsOnAudioPerfectPitchExercisePickedHandler(get(PianoKeyboardRepository.class), get(PuzzleConfigRepository.class));

        } else {
            throw new IllegalArgumentException("DI dependency is not found: " + someClass.getName());
        }
        return (T) impl;
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
