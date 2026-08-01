package org.swetlokognatsk.earking_out;

import org.springframework.boot.SpringApplication;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregateTest;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

@Deprecated
/** the time needed to complete 217 unit tests is about 1 minute, that is too much. so, no any spring contexts in unit tests */
public final class TestHelper {

    public static void buildNewSpringContext() {
        DI.refreshDependencies();
        var context = SpringApplication.run(SessionAggregateTest.class);
        DI.setContext(context);
    }
}
