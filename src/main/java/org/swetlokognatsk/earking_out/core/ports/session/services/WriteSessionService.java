package org.swetlokognatsk.earking_out.core.ports.session.services;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public interface WriteSessionService {
    <PCDTO extends PuzzleConfigDTO<?>> void createSession(PCDTO puzzleConfig);
}
