package org.swetlokognatsk.earking_out.core.domain.model.exercises;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

public abstract class Exercise extends ValueObject {
    public ExerciseNames name;
    public ExerciseTypes type;

    public abstract String tName();

    public abstract String tType();

    public Exercise(ExerciseNames name, ExerciseTypes type) {
        this.name = name;
        this.type = type;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Exercise) || obj == null) {
            return false;
        }

        var other = (Exercise) obj;
        return name == other.name && type == other.type;
    }

    public int hashCode() {
        return name.hashCode() + type.hashCode();
    }
}
