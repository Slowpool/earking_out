package org.swetlokognatsk.earking_out.app.desktop;

import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import javafx.application.Application;
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
    private static final int WIDTH = 1500;
    private static final int HEIGHT = 700;
    public static final int LABEL_FIELD_SPACING = 10;

    private BorderPane contentPane;
    private PerfectPitchConfigPane perfectPitchConfigPane;
    // private Pane perfectPitchConfigPane;

    public void start(Stage primaryStage) throws Exception {
        var scene = buildScene();

        configurePrimaryStage(primaryStage, scene);
        primaryStage.show();
    }

    private Scene buildScene() {
        var borderPane = new BorderPane();
        this.contentPane = borderPane;
        buildMenu(borderPane);
        var scene = new Scene(borderPane, WIDTH, HEIGHT);
        return scene;
    }

    private void buildMenu(BorderPane borderPane) {
        // TODO refactor it via a new MenuBuilder class
        var exercises = new Menu("exercises");
        var exercisesItems = exercises.getItems();

        var perfectPitch = new Menu("perfect pitch");
        var perfectPitchItems = perfectPitch.getItems();
        perfectPitch.setOnAction((e) -> {
            borderPane.setCenter(getPerfectPitchConfigPane());
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
        borderPane.setTop(menu);
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
        // TODO 1. add app state 2. change the sceneContent to exercise, corresponding to state, not corresponding to some data in `e` variable (it can lie so it's error-prone)
        switch(e.config)
    }

    private void configurePrimaryStage(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setTitle(Invariants.APP_NAME);
    }

    public static void main(String[] args) {
        launch();
    }
}
