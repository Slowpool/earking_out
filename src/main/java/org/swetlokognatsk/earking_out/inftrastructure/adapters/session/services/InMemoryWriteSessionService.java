package org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.session.services.WriteSessionService;

public final class InMemoryWriteSessionService implements WriteSessionService {
    public <PC extends PuzzleConfigAggregate<?>> void createSession(PC puzzleConfig) {
        var sessionStats = new SessionStats(5, 10);
        var newSession = new Session<PC>(UUID.randomUUID(), puzzleConfig, sessionStats);
        InMemoryReadSessionService.session = newSession;
    }

}
