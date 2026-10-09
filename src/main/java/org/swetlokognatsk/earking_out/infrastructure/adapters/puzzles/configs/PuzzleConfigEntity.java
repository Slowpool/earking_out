package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import jakarta.persistence.*;

@Entity
@Table(name = "puzzle_configs")
@IdClass(PuzzleConfigId.class)
public class PuzzleConfigEntity {

    @Id
    // TODO use Exercise instead
    private String exercise;

    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "serialized_config", nullable = false)
    private String serializedConfig;

    public final String getExercise() {
        return exercise;
    }

    public final int getUserId() {
        return userId;
    }

    public final String getSerializedPuzzleConfig() {
        return serializedConfig;
    }

    public final void setSerializedPuzzleConfig(final String serializedConfig) {
        this.serializedConfig = serializedConfig;
    }

    public PuzzleConfigEntity() {
    }

    public PuzzleConfigEntity(final int userId, final String exercise, final String serializedConfig) {
        this.exercise = exercise;
        this.userId = userId;
        this.serializedConfig = serializedConfig;
    }
}
