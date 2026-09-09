package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;

import jakarta.persistence.*;

@Entity
@Table(name = "puzzle_configs")
public class PuzzleConfigEntity {

    @Id
    // TODO use Exercise instead
    private String exercise;

    @Column(name = "serialized_config")
    private String serializedConfig;

    public Exercise getExercise() {
        return 
    }
    
    public PuzzleConfigEntity() {
        
    }
}
