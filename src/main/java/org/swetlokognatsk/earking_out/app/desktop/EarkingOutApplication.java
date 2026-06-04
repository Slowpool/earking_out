package org.swetlokognatsk.earking_out.app.desktop;

import org.swetlokognatsk.earking_out.app.desktop.components.ExercisesMenu;
import org.swetlokognatsk.earking_out.app.desktop.events.configs.ConfigPropertyUpdatingEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedOverEvent;
import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.ConfigPanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PuzzlePanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.StatsPanesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;
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

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 700;

    private final BorderPane contentPane;
    private final ExercisesMenu exercisesMenu;
    private final Scene mainScene;

    public static void main(String[] args) {
        // TODO wash away this hack after setting up the spring boot
        DI.env = DI.PROD_ENV;
        launch();
    }

    public EarkingOutApplication() {
        contentPane = buildContentPane();
        exercisesMenu = buildExercisesMenu();
        buildAndDisplayMenu();
        mainScene = buildMainScene();

    }

    private BorderPane buildContentPane() {
        var contentPane = new BorderPane();
        return contentPane;
    }

    protected ExercisesMenu buildExercisesMenu() {
        var exercisesMenu = new ExercisesMenu("exercises", this::openConfigPane);
        return exercisesMenu;
    }

    private void buildAndDisplayMenu() {
        var menu = new MenuBar(exercisesMenu);
        contentPane.setTop(menu);
    }

    private Scene buildMainScene() {
        var scene = new Scene(contentPane, WIDTH, HEIGHT);
        return scene;
    }

    public void start(Stage primaryStage) throws Exception {
        configurePrimaryStage(primaryStage);
        primaryStage.show();
    }

    private void configurePrimaryStage(Stage primaryStage) {
        primaryStage.setScene(mainScene);
        primaryStage.setTitle(Invariants.APP_NAME);
    }

    private void showAsContent(Pane pane) {
        contentPane.setCenter(pane);
    }

    private void openConfigPane(ActionEvent e) {
        var menuItem = (MenuItem) e.getTarget();
        Exercise exercise = (Exercise) menuItem.getUserData();
        showConfigPane(exercise);
    }

    private void showConfigPane(Exercise exercise) {
        var configPane = buildConfigPane(exercise.getClass(), exercise);
        showAsContent(configPane);
    }

    private <E extends Exercise, CP extends ConfigPane<E, ? extends PuzzleConfig<E>>> CP buildConfigPane(Class<E> exerciseClass, Exercise exercise) {
        if (!exerciseClass.equals(exercise.getClass())) {
            throw new IllegalArgumentException("exercise class does not correspond to exerciseClass");
        }

        var puzzleConfigService = DI.get(ReadPuzzleConfigService.class);
        var puzzleConfig = puzzleConfigService.fetch(exerciseClass, exercise);

        var configPane = ConfigPanesFactory.create(puzzleConfig, WIDTH, HEIGHT);
        configPane.addEventHandler(ExerciseStartedEvent.EXERCISE_STARTED, this::tryOpenPuzzlePane);
        configPane.addEventHandler(ConfigPropertyUpdatingEvent.CONFIG_PROPERTY_UPDATING, this::updateConfigProperty);
        return (CP) configPane;
    }

    private void tryOpenPuzzlePane(ExerciseStartedEvent<?> e) {
        var readPuzzleConfigService = DI.get(ReadPuzzleConfigService.class);
        var exercise = e.exercise;
        var puzzleConfig = readPuzzleConfigService.fetch(exercise.getClass(), exercise);

        if (puzzleConfig.isValid()) {
            var session = startSession(puzzleConfig);
            showPuzzlePane(session);
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

    private void showPuzzlePane(Session<?> session) {
        var puzzlePane = buildPuzzlePane(session);
        showAsContent(puzzlePane);
    }

    // TODO it definitely must be somewhere else, not here. though, it mustn't be encapsulated inside configPage.
    // app-level delivering mechanism
    private void updateConfigProperty(ConfigPropertyUpdatingEvent e) {
        // TODO can app service be skipped here so that the infrastructure service is used here instead?
        var puzzleConfigService = DI.get(PuzzleConfigService.class);
        puzzleConfigService.updateProperty(e.exercise, e.configProperty, e.newValue);
    }

    private void openConfigPaneOver(ExerciseStartedOverEvent<?> e) {
        exercisesMenu.fireExercise(e.puzzleConfig.exercise);
    }

    private Pane buildPuzzlePane(Session<? extends PuzzleConfig<?>> session) {
        var puzzlePane = PuzzlePanesFactory.create(session, WIDTH, HEIGHT);

        puzzlePane.addEventHandler(ExerciseFinishedEvent.EXERCISE_FINISHED, this::openExerciseFinish);

        return puzzlePane;
    }

    private void openExerciseFinish(ExerciseFinishedEvent e) {
        showExerciseFinishPane(e.session);
        // TODO actually exerciseFinishingService.finish(e.session) should be here
        closeSession(e.session);
    }

    private void showExerciseFinishPane(Session<?> session) {
        var sessionStatsPane = buildSessionStatsPane(session);
        showAsContent(sessionStatsPane);
    }

    private void closeSession(Session<?> session) {
        // TODO delegate to service
        session = null;
    }

    private Pane buildSessionStatsPane(Session<?> session) {
        var sessionStatsPane = StatsPanesFactory.create(session);
        sessionStatsPane.addEventHandler(ExerciseStartedOverEvent.EXERCISE_STARTED_OVER, this::openConfigPaneOver);
        return sessionStatsPane;
    }
}
