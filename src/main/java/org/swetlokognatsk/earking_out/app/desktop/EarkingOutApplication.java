package org.swetlokognatsk.earking_out.app.desktop;

import java.util.UUID;
import org.swetlokognatsk.earking_out.app.desktop.components.ExercisesMenu;
import org.swetlokognatsk.earking_out.app.desktop.events.configs.ConfigPropertyUpdatingEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedOverEvent;
import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPaneFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.AudioPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.VisualPerfectPitchPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats.PerfectPitchStatsPane;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.config.WritePuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.session.services.ReadSessionService;
import org.swetlokognatsk.earking_out.core.ports.session.services.WriteSessionService;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
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

    private Session<?> session;

    private BorderPane contentPane;
    private ExercisesMenu exercisesMenu;

    public void start(Stage primaryStage) throws Exception {
        var scene = buildScene();

        configurePrimaryStage(primaryStage, scene);
        primaryStage.show();
    }

    private Scene buildScene() {
        contentPane = new BorderPane();
        buildAndDisplayMenu();
        var scene = new Scene(contentPane, WIDTH, HEIGHT);
        return scene;
    }

    private void buildAndDisplayMenu() {
        exercisesMenu = new ExercisesMenu("exercises", this::openConfigPane);

        var menu = new MenuBar(exercisesMenu);
        contentPane.setTop(menu);
    }

    private void show(Pane pane) {
        contentPane.setCenter(pane);
    }

    private void openConfigPane(ActionEvent e) {
        var menuItem = (MenuItem) e.getTarget();
        showConfigPane((Exercise) menuItem.getUserData());
    }

    private <E extends Exercise> void showConfigPane(E exercise) {
        var configPane = buildConfigPane(exercise);
        show(configPane);
    }

    private <E extends Exercise, CP extends ConfigPane<E, ? extends PuzzleConfig<E>>> CP buildConfigPane(E exercise) {
        var puzzleConfigService = DI.get(ReadPuzzleConfigService.class);
        var puzzleConfig = puzzleConfigService.fetch(exercise);

        var configPane = ConfigPaneFactory.create(puzzleConfig, WIDTH, HEIGHT);
        configPane.addEventHandler(ExerciseStartedEvent.EXERCISE_STARTED, this::tryOpenPuzzlePane);
        configPane.addEventHandler(ConfigPropertyUpdatingEvent.CONFIG_PROPERTY_UPDATING, this::updateConfigProperty);
        return (CP) configPane;
    }

    private void tryOpenPuzzlePane(ExerciseStartedEvent<?> e) {
        var readPuzzleConfigService = DI.get(ReadPuzzleConfigService.class);
        var puzzleConfig = readPuzzleConfigService.fetch(e.exercise);

        if (puzzleConfig.isValid()) {
            session = startSession(puzzleConfig);
            showPuzzlePane();
        } else {
            // TODO message
            // DialogPane.
        }
    }

    private static <PC extends PuzzleConfig<?>> Session<PC> startSession(PC puzzleConfig) {
        var writeSessionService = DI.get(WriteSessionService.class);
        writeSessionService.createSession(puzzleConfig);

        var readSessionService = DI.get(ReadSessionService.class);
        var session = (Session<PC>) readSessionService.getCurrentSession();

        return session;
    }

    private void showPuzzlePane() {
        var puzzlePane = buildPuzzlePane();
        show(puzzlePane);
    }

    private void updateConfigProperty(ConfigPropertyUpdatingEvent e) {
        var writePuzzleConfigService = DI.get(WritePuzzleConfigService.class);
        writePuzzleConfigService.updateProperty(e.exercise, e.configProperty, e.newValue);
    }

    private void openConfigPaneOver(ExerciseStartedOverEvent<?> e) {
        exercisesMenu.fireExercise(e.puzzleConfig.exercise);
    }

    // TODO maybe just passing only config/exercise?
    private Pane buildPuzzlePane() {
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
