package org.swetlokognatsk.earking_out;

import java.util.Locale;
import java.util.concurrent.Executor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class SpringApp {

    public static Build build;

    public static final String IS_DESKTOP_BUILD = "T(org.swetlokognatsk.earking_out.SpringApp).build == T(org.swetlokognatsk.earking_out.Build).DESKTOP";
    public static final String IS_WEB_BUILD = "T(org.swetlokognatsk.earking_out.SpringApp).build == T(org.swetlokognatsk.earking_out.Build).WEB";

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

    @Bean
    LocaleResolver localeResolver() {
        var localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
}
