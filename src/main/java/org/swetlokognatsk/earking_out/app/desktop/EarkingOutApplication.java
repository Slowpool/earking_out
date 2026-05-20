package org.swetlokognatsk.earking_out.app.desktop;

import java.util.UUID;
import org.swetlokognatsk.earking_out.app.desktop.events.configs.ConfigPropertyUpdatingEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedOverEvent;
import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config.AudioPerfectPitchConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.AudioPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.VisualPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats.PerfectPitchStatsPane;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.config.WritePuzzleConfigService;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class EarkingOutApplication extends Application {
    public static final int LABEL_FIELD_SPACING = 10;
    // TODO make minimalWidth property to be equal to maximum screen width
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 700;

    private BorderPane contentPane;

    private AppState state;
    private Session<?> session;

    private Menu perfectPitch;

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

        perfectPitch = new Menu("perfect pitch");
        var perfectPitchItems = perfectPitch.getItems();
        // TODO make menu item mapping to specific configPane class
        perfectPitch.setOnAction(this::openConfigPane);

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

    private void show(Pane pane) {
        contentPane.setCenter(pane);
    }

    private void openConfigPane(ActionEvent e) {
        // TODO take exercise from e
        showConfigPane(new AudioPerfectPitchExercise());
    }

    private <E extends Exercise> void showConfigPane(E exercise) {
        var configPane = buildConfigPane(exercise);
        show(configPane);
    }

    // TODO redo via some ActionEvent parameter, this method must be factory
    // TODO use this signature
    // private <E extends Exercise, PC extends PuzzleConfig<E>> ConfigPane<E, PC> buildConfigPane(E exercise) {
    private <E extends Exercise, PC extends PuzzleConfig<E>> ConfigPane<AudioPerfectPitchExercise, AudioPerfectPitchConfig> buildConfigPane(E exercise) {
        var puzzleConfigService = DI.get(ReadPuzzleConfigService.class);
        var puzzleConfig = (AudioPerfectPitchConfig) puzzleConfigService.fetch(exercise);
        var perfectPitchConfigPane = new AudioPerfectPitchConfigPane(puzzleConfig, WIDTH, HEIGHT);
        perfectPitchConfigPane.addEventHandler(ExerciseStartedEvent.EXERCISE_STARTED, this::tryOpenPuzzlePane);
        perfectPitchConfigPane.addEventHandler(ConfigPropertyUpdatingEvent.CONFIG_PROPERTY_UPDATING, this::updateConfigProperty);
        return perfectPitchConfigPane;
    }

    private void tryOpenPuzzlePane(ExerciseStartedEvent<?> e) {
        var readPuzzleConfigService = DI.get(ReadPuzzleConfigService.class);
        var puzzleConfig = readPuzzleConfigService.fetch(e.exercise);

        if (puzzleConfig.isValid()) {
            // TODO generalize
            session = startSession((AudioPerfectPitchConfig) puzzleConfig);
            showPuzzlePane(session);
        } else {
            // TODO message
            // DialogPane.
        }
    }

    private void showPuzzlePane(Session<?> session) {
        var puzzlePane = buildPuzzlePane(session);
        show(puzzlePane);
    }

    private void updateConfigProperty(ConfigPropertyUpdatingEvent e) {
        var writePuzzleConfigService = DI.get(WritePuzzleConfigService.class);
        writePuzzleConfigService.updateProperty(e.exercise, e.configProperty, e.newValue);
    }

    private void openConfigPaneOver(ExerciseStartedOverEvent<?> e) {
        // TODO click menu item depending on e
        perfectPitch.fire();
    }

    // TODO replace with service call
    // private <E extends Exercise, PC extends PuzzleConfig<E>> Session<E, PC> startSession(PC puzzleConfig) {
    private Session<AudioPerfectPitchConfig> startSession(AudioPerfectPitchConfig puzzleConfig) {
        return new Session<AudioPerfectPitchConfig>(UUID.randomUUID(), puzzleConfig, new SessionStats(5, puzzleConfig.targetNumberOfPuzzles));
    }

    // TODO maybe just passing only config/exercise?
    private Pane buildPuzzlePane(Session<?> session) {
        var exercise = session.puzzleConfig().exercise;
        var puzzlePane = switch (exercise.type) {
        case VISUAL -> switch (exercise.name) {
        // TODO what to do with warning
        case PERFECT_PITCH -> new VisualPerfectPitchPane((Session<VisualPerfectPitchConfig>) session);
        case MELODIC_INTERVALS -> null;
        case HARMONIC_INTERVALS -> null;
        case KEYS -> null;
        };
        case AUDIO -> switch (exercise.name) {
        case PERFECT_PITCH -> new AudioPerfectPitchPane((Session<AudioPerfectPitchConfig>) session);
        case MELODIC_INTERVALS -> null;
        case HARMONIC_INTERVALS -> null;
        case KEYS -> null;
        };
        };

        puzzlePane.addEventHandler(PuzzlePane.EXERCISE_FINISHED, this::openExerciseFinish);

        return puzzlePane;
    }

    private void openExerciseFinish(ExerciseFinishedEvent e) {
        // TODO don't pass session, replace with something narrowed (e.g. only exercise or stats)
        showExerciseFinishPane(e.session);
        closeSession();
    }

    private void showExerciseFinishPane(Session<?> session) {
        var sessionStatsPane = buildSessionStatsPane();
        show(sessionStatsPane);
    }

    private void closeSession() {
        // TODO delegate to service
        session = null;
    }

    // TODO extract into special class
    private Pane buildSessionStatsPane() {
        // TODO factory method? this warning bothers a lot
        var sessionStatsPane = new PerfectPitchStatsPane(session);
        sessionStatsPane.addEventHandler(ExerciseStartedOverEvent.EXERCISE_STARTED_OVER, this::openConfigPaneOver);
        return sessionStatsPane;
    }

    private void configurePrimaryStage(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setTitle(Invariants.APP_NAME);
    }

    public static void main(String[] args) {
        launch();
    }
}
