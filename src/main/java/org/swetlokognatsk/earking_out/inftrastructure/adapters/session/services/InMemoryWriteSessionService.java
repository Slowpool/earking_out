package org.swetlokognatsk.earking_out.inftrastructure.adapters.session.services;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.session.services.WriteSessionService;

public final class InMemoryWriteSessionService implements WriteSessionService {
    public <PCDTO extends PuzzleConfigDTO<?>> void createSession(PCDTO puzzleConfig) {
        var sessionStats = new SessionStats(5, 10);
        var newSession = new Session<PCDTO>(UUID.randomUUID(), puzzleConfig, sessionStats);
        InMemoryReadSessionService.session = newSession;
    }

}
