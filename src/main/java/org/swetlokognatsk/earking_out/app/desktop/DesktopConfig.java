package org.swetlokognatsk.earking_out.app.desktop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import static org.swetlokognatsk.earking_out.SpringProfiles.DESKTOP;
import javax.sql.DataSource;
import org.springframework.core.env.Environment;

@Configuration
@Profile(DESKTOP)
public class DesktopConfig {

    @Bean
    @Primary
    @Profile(DESKTOP)
    DataSource dataSource(final Environment env) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        return dataSource;
    }

}
