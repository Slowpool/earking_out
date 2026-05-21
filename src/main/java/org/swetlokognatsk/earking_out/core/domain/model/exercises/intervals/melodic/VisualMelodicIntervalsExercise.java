package org.swetlokognatsk.earking_out.core.domain.model.exercises.intervals.melodic;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;

public class VisualMelodicIntervalsExercise extends Exercise {

    public VisualMelodicIntervalsExercise() {
        super(ExerciseNames.MELODIC_INTERVALS, ExerciseTypes.VISUAL);
    }

    public String tName() {
        return "melodic intervals";
    }

    public String tType() {
        return "visual";
    }
}
