package org.swetlokognatsk.earking_out.core.domain.model.solutions;

import java.io.Serializable;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;

public abstract class Solution extends ValueObject implements Serializable {

    public final Exercise exercise;

    public Solution(final Exercise exercise) {
        this.exercise = exercise;
    }
}
