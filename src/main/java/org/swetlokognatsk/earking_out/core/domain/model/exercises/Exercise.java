package org.swetlokognatsk.earking_out.core.domain.model.exercises;

// TODO polymorphism using is under question, probably one Exercise class is sufficient 
public abstract class Exercise {
    public ExerciseNames name;
    public ExerciseTypes type;

    public Exercise(ExerciseNames name, ExerciseTypes type) {
        this.name = name;
        this.type = type;
    }
}
