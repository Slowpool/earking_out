package org.swetlokognatsk.earking_out;

import static org.swetlokognatsk.earking_out.app.web.SessionAttributes.LOCALE;
import java.util.Locale;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import jakarta.servlet.http.HttpSession;

@SpringBootApplication
public class SpringApp {

    public static Build build;

    public static final String IS_DESKTOP_BUILD = "T(org.swetlokognatsk.earking_out.SpringApp).build == T(org.swetlokognatsk.earking_out.Build).DESKTOP";
    public static final String IS_WEB_BUILD = "T(org.swetlokognatsk.earking_out.SpringApp).build == T(org.swetlokognatsk.earking_out.Build).WEB";

    private static final Locale DEFAULT_LOCALE = new Locale("ru");

    @Bean
    Executor getTaskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(1000);
        executor.setThreadFactory((Runnable runnable) -> {
            var thread = new Thread(runnable);
            thread.setPriority(Thread.MIN_PRIORITY);
            return thread;
        });
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(10);

        executor.initialize();

        return executor;
    }

    // TODO decouple from web
    @Bean
    LocaleResolver localeResolver() {
        var localeResolver = new SessionLocaleResolver();

        localeResolver.setLocaleAttributeName(LOCALE);

        // TODO refactoring. it should be in a special locale resolver class
        localeResolver.setDefaultLocaleFunction(request -> {
            var userResolver = DI.get(UserResolver.class);

            var userId = userResolver.getCurrentUserId();
            if (userId == null) {
                return DEFAULT_LOCALE;
            }

            var userRepository = DI.get(UserRepository.class);
            var user = userRepository.get(userId);

            var lang = user.getLang();
            var locale = new Locale(lang.value);
            WebUtils.setSessionAttribute(request, LOCALE, locale);

            return locale;
        });
        return localeResolver;
    }
}
