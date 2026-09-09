package org.swetlokognatsk.earking_out.infrastructure.adapters.spring;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

// TODO sort it out. either remove or add to spring scanning.
@Configuration
public class SqliteConfig {
    @Bean
    DataSource dataSource(final Environment env) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("driverClassName"));
        dataSource.setUrl(env.getProperty("url"));
        return dataSource;
    }

}
