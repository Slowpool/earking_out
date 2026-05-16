package org.swetlokognatsk.earking_out.core.ports.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public interface ReadPuzzleConfigService {
    // TODO do i correctly understand that there's no diffrence between this and `<E extends Exercise, PC extends PuzzleConfig<E>> PuzzleConfig<E> fetch(E exercise);` cuz return type will always be runtime-calculated?
    <E extends Exercise> PuzzleConfig<E> fetch(E exercise);
}
