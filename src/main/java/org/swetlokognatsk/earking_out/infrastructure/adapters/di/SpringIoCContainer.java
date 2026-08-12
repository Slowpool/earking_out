package org.swetlokognatsk.earking_out.infrastructure.adapters.di;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.support.GenericApplicationContext;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeyboardHandlersRegister;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.ConfigPanesFactory;
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
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.EndSessionAggregateDTOAssemblersFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.IoCContainer;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.SerializationCloner;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.JacksonJsonSerializer;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring.SpringEventBus;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring.SpringEventPublisher;
import org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing.SQLiteEventStore;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.HintDemonstratorDelegator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.AudioClipPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.InMemoryPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.PianoKeySoundFilesBuilder;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs.SQLitePuzzleConfigRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.session.perfectpitch.InMemoryAudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.factories.puzzles.generators.SolutionGeneratorsFactory;

public final class SpringIoCContainer implements IoCContainer {

    public ApplicationContext context;

    public void setContext(final ApplicationContext context) {
        this.context = context;
        initBeans();
    }

    private void initBeans() {
        // TODO this cast seems awkward
        var ctx = (GenericApplicationContext) context;

        ctx.registerBean(AudioClipPianoKeySoundsPlayer.class);

        ctx.registerBean(NotesNormalizingService.class);

        ctx.registerBean(PianoKeyColorService.class);

        ctx.registerBean(HintDemonstratorDelegator.class);

        ctx.registerBean(SerializationCloner.class);

        ctx.registerBean(PianoKeysFactory.class, () -> new PianoKeysFactory(ctx.getBean(PianoKeyColorService.class)));

        // TODO pretty sure some suppliers are redundant
        ctx.registerBean(PianoKeyboardAggregatesFactory.class, () -> new PianoKeyboardAggregatesFactory(ctx.getBean(ObjectCloner.class), ctx.getBean(PianoKeysFactory.class)));
        ctx.registerBean(InMemoryPianoKeyboardRepository.class, () -> new InMemoryPianoKeyboardRepository(ctx.getBean(PianoKeyboardAggregatesFactory.class), ctx.getBean(PianoKeyboardDtoAssembler.class)));

        ctx.registerBean(AbstractPuzzleConfigAggregatesFactory.class, () -> new AbstractPuzzleConfigAggregatesFactory(ctx.getBean(ObjectCloner.class)));

        ctx.registerBean(PuzzleConfigService.class, () -> new PuzzleConfigService(ctx.getBean(PuzzleConfigRepository.class), ctx.getBean(PianoKeyboardRepository.class)));

        ctx.registerBean(PianoKeyboardService.class, () -> new PianoKeyboardService(ctx.getBean(PianoKeyboardRepository.class), ctx.getBean(AudioPerfectPitchSessionRepository.class)));

        ctx.registerBean(SolutionGeneratorsFactory.class);

        ctx.registerBean(PuzzlesFactory.class, () -> new PuzzlesFactory(ctx.getBean(SolutionGeneratorsFactory.class), ctx.getBean(PuzzleConfigDTOAssembler.class)));

        ctx.registerBean(SessionAggregatesFactory.class, () -> new SessionAggregatesFactory(ctx.getBean(ObjectCloner.class), ctx.getBean(PuzzleConfigRepository.class), ctx.getBean(PianoKeyboardRepository.class), ctx.getBean(PuzzleConfigDTOAssembler.class), ctx.getBean(PuzzlesFactory.class)));

        ctx.registerBean(AudioPerfectPitchSessionService.class, () -> new AudioPerfectPitchSessionService(ctx.getBean(PuzzleConfigRepository.class), ctx.getBean(AudioPerfectPitchSessionRepository.class), ctx.getBean(SessionAggregatesFactory.class)));

        ctx.registerBean(SessionRepositoryDelegator.class);

        ctx.registerBean(PianoKeySoundFilesBuilder.class);

        ctx.registerBean(PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator.class, () -> new PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator(ctx.getBean(PianoKeySoundsPlayer.class)));

        ctx.registerBean(InMemoryAudioPerfectPitchSessionRepository.class, () -> new InMemoryAudioPerfectPitchSessionRepository(ctx.getBean(SessionAggregatesFactory.class)));

        ctx.registerBean(EndSessionAggregateDTOAssemblersFactory.class);

        ctx.registerBean(PianoKeyboardDtoAssembler.class, () -> new PianoKeyboardDtoAssembler());

        ctx.registerBean(DomainEventsFactory.class);

        ctx.registerBean(SpringEventPublisher.class, () -> new SpringEventPublisher((ApplicationEventPublisher) context));

        ctx.registerBean(SpringEventBus.class);

        ctx.registerBean(PuzzleConfigDTOAssembler.class, () -> new PuzzleConfigDTOAssembler(ctx.getBean(PuzzleConfigRepository.class)));

        ctx.registerBean(RandomAudioPerfectPitchSolutionGenerator.class, (BeanDefinition bd) -> bd.setScope(BeanDefinition.SCOPE_PROTOTYPE));

        ctx.registerBean(SoundPlayerOnPianoKeyPressedHandler.class, () -> new SoundPlayerOnPianoKeyPressedHandler(ctx.getBean(PianoKeySoundsPlayer.class), ctx.getBean(PianoKeyboardRepository.class), ctx.getBean(PuzzleConfigRepository.class)));

        ctx.registerBean(HintDemonstratingOnNewPuzzleCreatedHandler.class, () -> new HintDemonstratingOnNewPuzzleCreatedHandler(ctx.getBean(HintDemonstratorDelegator.class)));

        ctx.registerBean(HintDemonstratingOnHintRepeatingRequestedHandler.class, () -> new HintDemonstratingOnHintRepeatingRequestedHandler(ctx.getBean(HintDemonstratorDelegator.class)));

        ctx.registerBean(SessionEventsLoggerHandler.class, () -> new SessionEventsLoggerHandler(ctx.getBean(EventStore.class)));

        ctx.registerBean(PuzzleConfigUpdatingOnPianoKeyPressedHandler.class, () -> new PuzzleConfigUpdatingOnPianoKeyPressedHandler(ctx.getBean(PuzzleConfigService.class)));

        // TODO pass service or what?
        ctx.registerBean(SessionGuessingOnPianoKeyPressedHandler.class, () -> new SessionGuessingOnPianoKeyPressedHandler(null));

        ctx.registerBean(SessionPianoKeyboardUpdatingOnSessionStartedHandler.class, () -> new SessionPianoKeyboardUpdatingOnSessionStartedHandler(ctx.getBean(PianoKeyboardService.class)));

        ctx.registerBean(InMemoryPuzzleConfigRepository.class, () -> new InMemoryPuzzleConfigRepository(ctx.getBean(AbstractPuzzleConfigAggregatesFactory.class)), (BeanDefinition bd) -> bd.setPrimary(true));

        ctx.registerBean(SQLitePuzzleConfigRepository.class, () -> new SQLitePuzzleConfigRepository(ctx.getBean(AbstractPuzzleConfigAggregatesFactory.class), ctx.getBean(PuzzleConfigJsonSerializer.class)));

        ctx.registerBean(JacksonJsonSerializer.class, () -> new JacksonJsonSerializer());

        // javafx beans
        ctx.registerBean(PuzzlePanesFactory.class, () -> new PuzzlePanesFactory(ctx.getBean(SessionRepositoryDelegator.class), ctx.getBean(PianoKeyboardHandlersRegister.class), ctx.getBean(PianoKeyboardService.class)));

        ctx.registerBean(StatsPanesFactory.class, () -> new StatsPanesFactory(ctx.getBean(PuzzleConfigRepository.class), ctx.getBean(SessionRepositoryDelegator.class)));

        ctx.registerBean(PianoKeyboardHandlersRegister.class, () -> new PianoKeyboardHandlersRegister(ctx.getBean(PianoKeyboardRepository.class)));

        ctx.registerBean(ConfigPanesFactory.class, () -> new ConfigPanesFactory(ctx.getBean(PianoKeyboardHandlersRegister.class)));

        // event sourcing
        ctx.registerBean(SQLiteEventStore.class, () -> new SQLiteEventStore(ctx.getBean(DomainEventJsonSerializer.class)));
    }

    public <T> T get(Class<T> someClass, Object... args) {
        return getBean(someClass, args);
    }

    private <T> T getBean(Class<T> someClass, Object... args) {
        try {
            var bean = args.length > 0 ? context.getBean(someClass, args) : context.getBean(someClass);
            return bean;
        } catch (BeansException e) {
            System.out.print("beans exception: " + e.getMessage());
            throw new RuntimeException("bean not found", e);
        }
    }

    public void refreshDependencies() {
        throw new IllegalStateException("spring dependencies refreshing is not supposed to be implemented/called");
    }

}
