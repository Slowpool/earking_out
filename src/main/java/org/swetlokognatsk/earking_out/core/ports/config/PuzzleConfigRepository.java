package org.swetlokognatsk.earking_out.core.ports.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRepository;

public interface PuzzleConfigRepository extends AggregateRepository<Exercise, PuzzleConfigAggregate<Exercise>> {
}
