package org.swetlokognatsk.earking_out.inftrastructure.adapters.di;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.support.GenericApplicationContext;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PuzzlePanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.StatsPanesFactory;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.HintRepeatingRequestedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.NewPuzzleCreatedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.PianoKeyPressedHandler;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.EndSessionAggregateDTOAssemblersFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators.SolutionGeneratorsFactory;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.IoCContainer;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.core.ports.piano.PuzzleConfigPianoKeyboardStorageAdapter;
import org.swetlokognatsk.earking_out.core.ports.piano.SessionPianoKeyboardStorageAdapter;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.base.SerializationCloner;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring.SpringEventBus;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring.SpringEventPublisher;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.HintDemonstratorDelegator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.sound.PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.AudioClipPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.InMemoryPuzzleConfigPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.InMemorySessionPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.PianoKeySoundFilesBuilder;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.TestInMemoryAllPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.session.perfectpitch.InMemoryAudioPerfectPitchSessionRepository;

public final class SpringIoCContainer implements IoCContainer {

    public ApplicationContext context;

    public void setContext(final ApplicationContext context) {
        this.context = context;
        initBeans();
    }

    private void initBeans() {
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

        genericContext.registerBean(AbstractPuzzleConfigAggregatesFactory.class, () -> new AbstractPuzzleConfigAggregatesFactory(genericContext.getBean(ObjectCloner.class)));

        genericContext.registerBean(InMemoryPuzzleConfigRepository.class, () -> new InMemoryPuzzleConfigRepository(genericContext.getBean(PuzzleConfigPianoKeyboardStorageAdapter.class), genericContext.getBean(AbstractPuzzleConfigAggregatesFactory.class)));

        genericContext.registerBean(InMemorySessionPianoKeyboardRepository.class, () -> new InMemorySessionPianoKeyboardRepository(genericContext.getBean(PianoKeyboardAggregatesFactory.class)));

        genericContext.registerBean(PuzzleConfigService.class, () -> new PuzzleConfigService(genericContext.getBean(PuzzleConfigRepository.class)));

        genericContext.registerBean(TestInMemoryAllPianoKeyboardRepository.class, () -> new TestInMemoryAllPianoKeyboardRepository(genericContext.getBean(PianoKeyboardAggregatesFactory.class)));

        genericContext.registerBean(SolutionGeneratorsFactory.class);

        genericContext.registerBean(PuzzlesFactory.class, () -> new PuzzlesFactory(genericContext.getBean(SolutionGeneratorsFactory.class), genericContext.getBean(PuzzleConfigDTOAssembler.class)));

        genericContext.registerBean(SessionAggregatesFactory.class, () -> new SessionAggregatesFactory(genericContext.getBean(ObjectCloner.class), genericContext.getBean(PuzzleConfigRepository.class), genericContext.getBean(SessionPianoKeyboardStorageAdapter.class), genericContext.getBean(PuzzleConfigDTOAssembler.class), genericContext.getBean(PuzzlesFactory.class)));

        genericContext.registerBean(AudioPerfectPitchSessionService.class, () -> new AudioPerfectPitchSessionService(genericContext.getBean(PuzzleConfigRepository.class), genericContext.getBean(AudioPerfectPitchSessionRepository.class), genericContext.getBean(SessionAggregatesFactory.class)));

        genericContext.registerBean(SessionRepositoryDelegator.class);

        genericContext.registerBean(PianoKeySoundFilesBuilder.class);

        genericContext.registerBean(PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator.class, () -> new PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator(genericContext.getBean(PianoKeySoundsPlayer.class)));

        genericContext.registerBean(InMemoryAudioPerfectPitchSessionRepository.class, () -> new InMemoryAudioPerfectPitchSessionRepository(genericContext.getBean(SessionAggregatesFactory.class), genericContext.getBean(SessionPianoKeyboardStorageAdapter.class)));

        genericContext.registerBean(EndSessionAggregateDTOAssemblersFactory.class);

        genericContext.registerBean(DomainEventsFactory.class);

        genericContext.registerBean(SpringEventPublisher.class, () -> new SpringEventPublisher((ApplicationEventPublisher) context));

        genericContext.registerBean(SpringEventBus.class);

        genericContext.registerBean(PuzzleConfigDTOAssembler.class, () -> new PuzzleConfigDTOAssembler(genericContext.getBean(PuzzleConfigRepository.class)));

        genericContext.registerBean(RandomAudioPerfectPitchSolutionGenerator.class, (BeanDefinition bd) -> bd.setScope(BeanDefinition.SCOPE_PROTOTYPE));

        genericContext.registerBean(PianoKeyPressedHandler.class, () -> new PianoKeyPressedHandler(genericContext.getBean(PianoKeySoundsPlayer.class), genericContext.getBean(PuzzleConfigPianoKeyboardStorageAdapter.class), genericContext.getBean(PuzzleConfigRepository.class)));

        genericContext.registerBean(NewPuzzleCreatedHandler.class, () -> new NewPuzzleCreatedHandler(genericContext.getBean(HintDemonstratorDelegator.class)));

        genericContext.registerBean(HintRepeatingRequestedHandler.class, () -> new HintRepeatingRequestedHandler(genericContext.getBean(HintDemonstratorDelegator.class)));

        // javafx beans
        genericContext.registerBean(PuzzlePanesFactory.class, () -> new PuzzlePanesFactory(genericContext.getBean(SessionRepositoryDelegator.class)));

        genericContext.registerBean(StatsPanesFactory.class, () -> new StatsPanesFactory(genericContext.getBean(PuzzleConfigRepository.class), genericContext.getBean(SessionRepositoryDelegator.class)));

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
