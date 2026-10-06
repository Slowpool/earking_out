package org.swetlokognatsk.earking_out.app.web.configs;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;

@EnableSpringHttpSession
@Configuration
class SessionConfig {

    // EOSESSIONID = Earking Out Session ID
    private static final String SESSION_COOKIE = "EOSESSIONID";

    @Bean
    SessionRepository<MapSession> sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }

    @Bean
    DefaultCookieSerializer cookieSerializer() {
        var serializer = new DefaultCookieSerializer();

        serializer.setCookieName(SESSION_COOKIE);

        return serializer;
    }

}
