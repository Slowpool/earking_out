package org.swetlokognatsk.earking_out.achievements_service.infrastructure;

public class World {
    public static final int MONOLITH_PORT = 8081;
    public static final String MONOLITH_URI = "http://earking-out-web:" + MONOLITH_PORT;
    public static final String MONOLITH_API_URI = MONOLITH_URI + "/api/inner/v1";
    public static final String MONOLITH_UNLOCKED_ACHIEVEMENTS_URI = MONOLITH_API_URI + "/unlocked-achievements";
    public static final String MONOLITH_DOMAIN_EVENTS_URI = MONOLITH_API_URI + "/domain-events";
}
