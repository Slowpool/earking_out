package org.swetlokognatsk.earking_out.core.ports.session.services;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public interface WriteSessionService {
    <PC extends PuzzleConfig<?>> void createSession(PC puzzleConfig);
}
