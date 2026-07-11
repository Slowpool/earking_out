package org.swetlokognatsk.earking_out.core.domain.model.exercises;

import java.io.Serializable;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

public abstract class Exercise extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;

    public final ExerciseNames name;
    public final ExerciseTypes type;

    public abstract String tName();

    public abstract String tType();

    public Exercise(final ExerciseNames name, final ExerciseTypes type) {
        // TODO apply these checks everywhere (at least in puzzleConfig, PianoKeyboard)
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
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
