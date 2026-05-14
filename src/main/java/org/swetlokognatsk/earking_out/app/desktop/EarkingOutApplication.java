package org.swetlokognatsk.earking_out.app.desktop;

import java.util.function.Consumer;
import java.util.function.Function;
import org.swetlokognatsk.earking_out.app.desktop.events.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.ExerciseStartedEvent;
import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.AudioPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.VisualPerfectPitchPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public final class EarkingOutApplication extends Application {
    public static final int LABEL_FIELD_SPACING = 10;
    private static final int WIDTH = 1500;
    private static final int HEIGHT = 700;

    private BorderPane contentPane;
    // TODO generalize
    private PerfectPitchConfigPane perfectPitchConfigPane;

    private AppState state;

    public void start(Stage primaryStage) throws Exception {
        state = AppState.HOME;
        var scene = buildScene();

        configurePrimaryStage(primaryStage, scene);
        primaryStage.show();
    }

    private Scene buildScene() {
        contentPane = new BorderPane();
        buildMenu();
        var scene = new Scene(contentPane, WIDTH, HEIGHT);
        return scene;
    }

    private void buildMenu() {
        // TODO refactor it via a new MenuBuilder class p.s. or create ExercisesMenu?
        var exercises = new Menu("exercises");
        var exercisesItems = exercises.getItems();

        var perfectPitch = new Menu("perfect pitch");
        var perfectPitchItems = perfectPitch.getItems();
        // TODO speicfy node id? to identify to which configuring scene to go
        perfectPitch.setOnAction((e) -> {
            contentPane.setCenter(getPerfectPitchConfigPane());
        });

        var audioPerfectPitch = new MenuItem("audio");
        perfectPitchItems.add(audioPerfectPitch);

        var visualPerfectPitch = new MenuItem("visual");
        perfectPitchItems.add(visualPerfectPitch);

        exercisesItems.add(perfectPitch);

        var melodicIntervals = new Menu("melodic intervals");
        var melodicIntervalsItems = melodicIntervals.getItems();

        var audioMelodicIntervals = new MenuItem("audio");
        melodicIntervalsItems.add(audioMelodicIntervals);

        var visualMelodicIntervals = new MenuItem("visual");
        melodicIntervalsItems.add(visualMelodicIntervals);

        exercisesItems.add(melodicIntervals);

        var menu = new MenuBar(exercises);
        contentPane.setTop(menu);
    }

    private PerfectPitchConfigPane getPerfectPitchConfigPane() {
        if (perfectPitchConfigPane == null) {
            perfectPitchConfigPane = new PerfectPitchConfigPane();
            // TODO decouple
            perfectPitchConfigPane.addEventHandler(PerfectPitchConfigPane.EXERCISE_STARTED, this::startExercise);
        }
        return perfectPitchConfigPane;
    }

    private void startExercise(ExerciseStartedEvent e) {
        var exercisePane = buildExercisePane(e.exercise, e.config);
        exercisePane.addEventHandler(PuzzlePane.EXERCISE_FINISHED, this::finishExercise);
        contentPane.setCenter(exercisePane);
    }

    // TODO generics seems to be redundant here, this is just factory-like steering method
    private <E extends Exercise, PC extends PuzzleConfig<E>> Pane buildExercisePane(E exercise, PC config) {
        return switch (exercise.type) {
        case VISUAL -> switch (exercise.name) {
        // TODO optimization via get method
        case PERFECT_PITCH -> new VisualPerfectPitchPane((VisualPerfectPitchConfig) config);
        case MELODIC_INTERVALS -> null;
        case HARMONIC_INTERVALS -> null;
        case KEYS -> null;
        };
        case AUDIO -> switch (exercise.name) {
        case PERFECT_PITCH -> new AudioPerfectPitchPane((AudioPerfectPitchConfig) config);
        case MELODIC_INTERVALS -> null;
        case HARMONIC_INTERVALS -> null;
        case KEYS -> null;
        };
        };
    }

    private void finishExercise(ExerciseFinishedEvent e) {
        var sessionStatisticsPane = buildSessionStatisticsPane();
        contentPane.setCenter(sessionStatisticsPane);

    }

    private Pane buildSessionStatisticsPane() {
        var titleLabel = new Label("finished");
        bazinga
        var titleLabelBox = new VBox(titleLabel);
        titleLabelBox.setAlignment(Pos.CENTER);

        return new VBox(titleLabelBox,);
    }

    private void configurePrimaryStage(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setTitle(Invariants.APP_NAME);
    }

    public static void main(String[] args) {
        launch();
    }
}
