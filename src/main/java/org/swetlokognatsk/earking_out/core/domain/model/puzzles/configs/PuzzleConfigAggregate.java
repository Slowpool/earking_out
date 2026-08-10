package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;

public abstract class PuzzleConfigAggregate<E extends Exercise> extends AggregateRoot<E> {
    private static final long serialVersionUID = 1L;

    public static final String TARGET_NUMBER_OF_PUZZLES_PROP = "targetNumberOfPuzzles";
    public static final String STATS_RECORDING_PROP = "statsRecording";

    protected int targetNumberOfPuzzles;
    protected boolean statsRecording;

    protected abstract void updateConfigSpecificProperty(final String propertyName, final Object propertyValue);

    public int getTargetNumberOfPuzzles() {
        return targetNumberOfPuzzles;
    }

    protected final void setTargetNumberOfPuzzles(final int targetNumberOfPuzzles) {
        this.targetNumberOfPuzzles = targetNumberOfPuzzles;
    }

    public boolean getStatsRecording() {
        return statsRecording;
    }

    protected final void setStatsRecording(final boolean statsRecording) {
        this.statsRecording = statsRecording;
    }

    public PuzzleConfigAggregate(final E exercise, final int targetNumberOfPuzzles, final boolean statsRecording) {
        super(exercise);

        setTargetNumberOfPuzzles(targetNumberOfPuzzles);
        setStatsRecording(statsRecording);
    }

    public final void updateProperty(final String propertyName, final Object propertyValue) {
        switch (propertyName) {
        case TARGET_NUMBER_OF_PUZZLES_PROP:
            setTargetNumberOfPuzzles((int) propertyValue);
            break;
        case STATS_RECORDING_PROP:
            setStatsRecording((boolean) propertyValue);
            break;
        default:
            updateConfigSpecificProperty(propertyName, propertyValue);
            break;
        }
    }
}
