package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import jakarta.persistence.*;

@Entity
@Table(name = "puzzle_configs")
public class PuzzleConfigEntity {

    @Id
    // TODO use Exercise instead
    private String exercise;

    @Column(name = "serialized_config")
    private String serializedConfig;

    public final String getExercise() {
        return exercise;
    }

    public final String getSerializedPuzzleConfig() {
        return serializedConfig;
    }

    public PuzzleConfigEntity() {

    }

    public PuzzleConfigEntity(final String exercise, final String serializedConfig) {
        this.exercise = exercise;
        this.serializedConfig = serializedConfig;
    }
}
