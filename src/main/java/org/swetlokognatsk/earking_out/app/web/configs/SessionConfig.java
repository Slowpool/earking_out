package org.swetlokognatsk.earking_out.app.web.configs;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;
import static org.swetlokognatsk.earking_out.SpringProfiles.WEB;

@EnableSpringHttpSession
@Configuration
@Profile(WEB)
class SessionConfig {

    // EOSESSIONID = Earking Out Session ID
    private static final String SESSION_COOKIE = "EOSESSIONID";

    private static final int SECONDS_IN_DAY = 86400;
    private static final int SESSION_COOKIE_MAX_AGE = SECONDS_IN_DAY * 10;

    @Bean
    @ConditionalOnMissingClass("org.springframework.session.data.redis.RedisSessionRepository")
    SessionRepository<MapSession> sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }

    @Bean
    DefaultCookieSerializer cookieSerializer() {
        var serializer = new DefaultCookieSerializer();

        serializer.setCookieName(SESSION_COOKIE);
        serializer.setCookieMaxAge(SESSION_COOKIE_MAX_AGE);

        return serializer;
    }

}
