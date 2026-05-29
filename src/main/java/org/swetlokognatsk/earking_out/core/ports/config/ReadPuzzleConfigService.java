package org.swetlokognatsk.earking_out.core.ports.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public interface ReadPuzzleConfigService {
    <E extends Exercise, PC extends PuzzleConfig<E>> PC fetch(Class<E> exerciseClass, Exercise exercise);
}
