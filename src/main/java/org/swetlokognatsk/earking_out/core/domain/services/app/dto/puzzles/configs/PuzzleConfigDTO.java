package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;

public abstract class PuzzleConfigDTO<E extends Exercise> {
    public final E exercise;
    public final int targetNumberOfPuzzles;
    public final boolean statsRecording;

    public PuzzleConfigDTO(final E exercise, final int targetNumberOfPuzzles, final boolean statsRecording) {
        this.exercise = exercise;
        this.targetNumberOfPuzzles = targetNumberOfPuzzles;
        this.statsRecording = statsRecording;
    }
}
