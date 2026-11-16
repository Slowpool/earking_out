package org.swetlokognatsk.earking_out.achievements_service.core.domain;

import java.util.HashMap;
import java.util.Map;

import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;

public class DomainEventsTestHelper {

    public static final UserId USER_ID = new UserId(1);
    public static final UserId ANOTHER_USER_ID = new UserId(2);
    public static final String PERFECT_PITCH = "PERFECT_PITCH";
    public static final String AUDIO = "AUDIO";
    
    public Map<String, Object> newProperties() {
        return new HashMap<>();
    }
}
