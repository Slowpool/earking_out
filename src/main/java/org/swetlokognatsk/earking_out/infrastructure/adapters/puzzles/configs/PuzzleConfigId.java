package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PuzzleConfigId {
    private String exercise;
    private int userId;
}
