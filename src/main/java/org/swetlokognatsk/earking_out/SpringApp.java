package org.swetlokognatsk.earking_out;

import java.util.concurrent.Executor;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class SpringApp {

    public static Build build;

    @Bean
    @Primary
    @ConditionalOnExpression("T(org.swetlokognatsk.earking_out.SpringApp).build == T(org.swetlokognatsk.earking_out.Build).DESKTOP")
    DataSource dataSource(final Environment env) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        return dataSource;
    }

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

}
