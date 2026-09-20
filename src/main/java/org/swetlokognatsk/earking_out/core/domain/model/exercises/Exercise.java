package org.swetlokognatsk.earking_out.core.domain.model.exercises;

import java.io.Serializable;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

public abstract class Exercise extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String TYPE_AND_NAME_SEPARATOR = "/";

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

    @Override
    public final String toString() {
        return "%s%s%s".formatted(type.toString(), TYPE_AND_NAME_SEPARATOR, name.toString());
    }

    public static final Exercise valueOf(final String value) {
        var parts = value.split(TYPE_AND_NAME_SEPARATOR);
        String typeValue = parts[0];
        String nameValue = parts[1];
        var type = ExerciseNames.valueOf(nameValue);
        var name = ExerciseTypes.valueOf(typeValue);
        return ExercisesFactory.create(type, name);
    }
}
