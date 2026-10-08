package org.swetlokognatsk.earking_out;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Profile;
import static org.swetlokognatsk.earking_out.SpringProfiles.TEST;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
})
@Profile(TEST)
public class TestConfig {

}
