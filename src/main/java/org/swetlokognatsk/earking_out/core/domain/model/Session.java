package org.swetlokognatsk.earking_out.core.domain.model;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

// TODO why record cannot extend? is it DTO?
public record Session<PCDTO extends PuzzleConfigDTO<?>>(UUID id, PCDTO puzzleConfigDto, SessionStats stats) {

}
