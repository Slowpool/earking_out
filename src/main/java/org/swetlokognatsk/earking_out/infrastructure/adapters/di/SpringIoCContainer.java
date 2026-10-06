package org.swetlokognatsk.earking_out.infrastructure.adapters.di;

import java.util.function.Function;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.swetlokognatsk.earking_out.SpringApp;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeyboardHandlersRegister;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.ConfigPanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PuzzlePanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.StatsPanesFactory;
import org.swetlokognatsk.earking_out.app.web.GuestUserInterceptor;
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
import org.swetlokognatsk.earking_out.core.domain.model.identity.DesktopUser;
import org.swetlokognatsk.earking_out.core.domain.model.identity.User;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactoryResolver;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.VisualPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.ExerciseService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.EndSessionAggregateDTOAssemblersFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.identity.UserService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.PerfectPitchSessionStatsService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesParsingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoSoundPolicyService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.perfectpitch.EditableAudioPerfectPitchConfigValidator;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.perfectpitch.FinalizedAudioPerfectPitchConfigValidator;
import org.swetlokognatsk.earking_out.core.domain.services.domain.session.perfectpitch.PerfectPitchSessionStatsAggregator;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.IoCContainer;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundFilesResolver;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.SerializationCloner;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring.SpringEventBus;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring.SpringEventPublisher;
import org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing.SpringJpaEventStore;
import org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing.SQLiteEventStore;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.HintDemonstratorDelegator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.AudioClipPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.InMemoryPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.SpringPianoKeySoundFilesResolver;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs.SQLitePuzzleConfigRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs.SpringJpaPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.serialization.JacksonJsonSerializer;
import org.swetlokognatsk.earking_out.infrastructure.adapters.session.perfectpitch.InMemoryAudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.factories.puzzles.generators.SolutionGeneratorsFactory;
import org.swetlokognatsk.earking_out.infrastructure.sounds.PianoKeySoundFilesBuilder;
import org.swetlokognatsk.earking_out.infrastructure.web.adapters.piano.FakeKeySoundsPlayer;
import jakarta.persistence.EntityManager;

public final class SpringIoCContainer implements IoCContainer {

    public ApplicationContext context;
    public GenericApplicationContext ctx;

    public void setContext(final ApplicationContext context) {
        this.context = context;
        this.ctx = (GenericApplicationContext) context;

        initBeans();
    }

    private void initBeans() {
        initSharedBeans();
        switch (SpringApp.build) {
        case DESKTOP:
            initDesktopBeans();
            break;
        case WEB:
            initWebBeans();
            break;
        default:
            throw new RuntimeException("unknown build: %s".formatted(SpringApp.build));
        }
    }

    private void initSharedBeans() {
    }

    private void initDesktopBeans() {
        ctx.registerBean(AudioClipPianoKeySoundsPlayer.class);

        ctx.registerBean(PianoKeySoundFilesBuilder.class, () -> new PianoKeySoundFilesBuilder(get(PianoKeySoundFilesResolver.class)));

        ctx.registerBean(SpringPianoKeySoundFilesResolver.class);

        // javafx beans
        ctx.registerBean(PuzzlePanesFactory.class, () -> new PuzzlePanesFactory(get(SessionRepositoryDelegator.class)));

        ctx.registerBean(StatsPanesFactory.class, () -> new StatsPanesFactory(get(PuzzleConfigRepository.class), get(SessionRepositoryDelegator.class)));

        ctx.registerBean(PianoKeyboardHandlersRegister.class, () -> new PianoKeyboardHandlersRegister(get(PianoKeyboardRepository.class)));

        ctx.registerBean(ConfigPanesFactory.class, () -> new ConfigPanesFactory(get(PianoKeyboardHandlersRegister.class)));

        // event sourcing
        ctx.registerBean(SQLiteEventStore.class, () -> new SQLiteEventStore(get(DomainEventJsonSerializer.class)), bd -> bd.setPrimary(false));

        // identity
        // TODO restore it back later
        // ctx.registerBean(DesktopUser.class);

        ctx.registerBean(PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator.class, () -> new PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator(get(PianoKeySoundsPlayer.class)));

        ctx.registerBean(UserAggregatesFactory.class);

        ctx.registerBean(UserService.class);

    }

    private void initWebBeans() {
        ctx.registerBean(FakeKeySoundsPlayer.class);

        ctx.registerBean(SpringPianoKeySoundFilesResolver.class);

        // TODO register user somehow. take it's identity from session cookie
        ctx.registerBean(GuestUserInterceptor.class);
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
        throw new IllegalStateException("this container does not support dependencies refreshing");
    }

    public <T> void register(final Class<T> someClass, final Function<Object[], T> depFactory) throws IllegalStateException {
        throw new IllegalStateException("this container does not support dynamic dependencies registering");
    }

    public <T> void register(final Class<T> someClass, T dependency) throws IllegalStateException {
        throw new IllegalStateException("this container does not support dynamic dependencies registering");
    }

}
