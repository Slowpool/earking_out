package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.services.puzzles.generators.PuzzleGeneratorsFactory;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class PuzzlePane<E extends Exercise, PC extends PuzzleConfig<E>, H extends Hint, PG extends PuzzleGenerator, P extends Puzzle<E, PC, H, PG>> extends BorderPane {
    // TODO why it's so awkward? do i use it in a wrong way?
    public static final EventType<ExerciseFinishedEvent> EXERCISE_FINISHED = new EventType<ExerciseFinishedEvent>("EXERCISE_FINISHED");

    protected final Session<PC> session;
    protected final PC puzzleConfig;
    protected final PG puzzleGenerator;
    protected P puzzle;

    protected final Pane puzzlePane;
    protected final ProgressBar puzzlesProgressBar;
    protected final Button finishButton;

    protected abstract Pane buildPuzzlePane();

    protected abstract void demonstrateNewHint();

    protected abstract void demonstrateHint();

    public PuzzlePane(final Session<PC> session, double width, double height) {
        setWidth(width);
        setHeight(height);

        this.session = session;
        this.puzzleConfig = session.puzzleConfig();
        this.puzzleGenerator = PuzzleGeneratorsFactory.create(puzzleConfig);

        var puzzleProgressLabel = new Label(interpolatePuzzleProgress(0, puzzleConfig.targetNumberOfPuzzles));
        puzzlesProgressBar = new ProgressBar(0.0);
        var puzzlesProgress = new VBox(puzzleProgressLabel, puzzlesProgressBar);
        puzzlesProgress.setAlignment(Pos.CENTER);
        setTop(puzzlesProgress);

        puzzlePane = buildPuzzlePane();
        setCenter(puzzlePane);

        finishButton = new Button("finish");
        finishButton.setOnAction(this::finishExercise);
        // frontend hack to align button
        var finishButtonBox = new VBox(finishButton);
        finishButtonBox.setAlignment(Pos.CENTER);
        setBottom(finishButtonBox);
    }

    private static String interpolatePuzzleProgress(int numberOfPuzzles, int targetNumberOfPuzzles) {
        return String.format("%d of %d are guessed", numberOfPuzzles, targetNumberOfPuzzles);
    }

    protected void finishExercise(ActionEvent e) {
        var exerciseFinishedEvent = new ExerciseFinishedEvent(EXERCISE_FINISHED, session);
        fireEvent(exerciseFinishedEvent);
    }

    protected void nextPuzzle() {
        createNextPuzzle();
        demonstrateNewHint();
    }

    protected void createNextPuzzle() {
        puzzle = PuzzlesFactory.create(puzzleConfig, puzzleGenerator);
    }

}
