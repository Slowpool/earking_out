package org.swetlokognatsk.earking_out.core.ports.session.services;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;

public interface WriteSessionService {
    <PC extends PuzzleConfigAggregate<?>> void createSession(PC puzzleConfig);
}
