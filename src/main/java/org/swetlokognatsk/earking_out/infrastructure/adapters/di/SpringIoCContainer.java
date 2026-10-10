package org.swetlokognatsk.earking_out.infrastructure.adapters.di;

import java.util.function.Function;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.swetlokognatsk.earking_out.SpringProfiles;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeyboardHandlersRegister;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.ConfigPanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PuzzlePanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.StatsPanesFactory;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.identity.UserService;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.IoCContainer;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundFilesResolver;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing.SQLiteEventStore;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.AudioClipPianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.SpringPianoKeySoundFilesResolver;
import org.swetlokognatsk.earking_out.infrastructure.sounds.PianoKeySoundFilesBuilder;

public final class SpringIoCContainer implements IoCContainer {

    public ApplicationContext context;
    public GenericApplicationContext ctx;
    private final String profile = System.getProperty("spring.profiles.active");

    public void setContext(final ApplicationContext context) {
        this.context = context;
        this.ctx = (GenericApplicationContext) context;
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
        // ctx.registerBean(FakeKeySoundsPlayer.class);

        // ctx.registerBean(SpringPianoKeySoundFilesResolver.class);

        // TODO register user somehow. take it's identity from session cookie
        // ctx.registerBean(GuestUserInterceptor.class);
    }

    public <T> T get(Class<T> someClass, Object... args) {
        return getBean(someClass, args);
    }

    private <T> T getBean(Class<T> someClass, Object... args) {
        try {
            var bean = args.length > 0
                    ? context.getBean(someClass, args)
                    : context.getBean(someClass);
            return bean;
        } catch (BeansException e) {
            System.out.print("beans exception: " + e.getMessage());
            throw new RuntimeException("bean not found", e);
        }
    }

    public void refreshDependencies() {
        throw new IllegalStateException("this container does not support dependencies refreshing");
    }

    public <T> void register(final Class<T> interfaceClass, final Class<? extends T> implementationClass) throws IllegalStateException {
        if (!profile.equals(SpringProfiles.TEST)) {
            throw new IllegalStateException("this container does not support dynamic dependencies registering");
        }

        var oldBeanNames = ctx.getBeanNamesForType(interfaceClass);
        if (oldBeanNames.length > 0) {
            for (var oldBeanName : oldBeanNames) {
                ctx.removeBeanDefinition(oldBeanName);
            }
        }
        ctx.registerBean(implementationClass);
    }

    public <T> void register(Class<T> interfaceClass, T object) throws IllegalStateException {
        if (!profile.equals(SpringProfiles.TEST)) {
            throw new IllegalStateException("this container does not support dynamic dependencies registering");
        }

        ctx.registerBean(interfaceClass, () -> object);
    }

}
