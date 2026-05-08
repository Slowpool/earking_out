package org.swetlokognatsk.earking_out.core.domain.model.exercises;

public abstract class Exercise {
    public ExerciseNames name;
    public ExerciseTypes type;

    public Exercise(ExerciseNames name, ExerciseTypes type) {
        this.name = name;
        this.type = type;
    }
}
