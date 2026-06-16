package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.base.Factory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;

public abstract interface PuzzleConfigAggregatesFactory<PCA extends PuzzleConfigAggregate<?>, DADTO extends DependentAggregatesDTO> extends Factory<PCA, DADTO> {

}
