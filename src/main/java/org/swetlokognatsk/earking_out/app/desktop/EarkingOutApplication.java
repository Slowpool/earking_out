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
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.exceptions.InvalidPuzzleConfigException;
import org.swetlokognatsk.earking_out.core.ports.DI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// TODO pianoKeyboard is not saved yet to repository, though it should be. in both `updateProperty()` and `updateViaPianoKeyboardPressing()`
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
        var configPane = buildConfigPane(exercise);
        showAsContent(configPane);
    }

    private <E extends Exercise, CP extends ConfigPane<E, ? extends PuzzleConfigDTO<E>>> CP buildConfigPane(final E exercise) {
        var puzzleConfigDto = PuzzleConfigDTOAssembler.getPuzzleConfigDTO(exercise);

        var configPane = ConfigPanesFactory.create(puzzleConfigDto, WIDTH, HEIGHT);
        configPane.addEventHandler(ExerciseStartedEvent.EXERCISE_STARTED, this::tryOpenPuzzlePane);
        configPane.addEventHandler(ConfigPropertyUpdatingEvent.CONFIG_PROPERTY_UPDATING, this::updateConfigProperty);
        return (CP) configPane;
    }

    private void tryOpenPuzzlePane(final ExerciseStartedEvent<?> event) {
        var sessionService = DI.get(SessionService.class);
        try {
            // TODO draft version
            var session = sessionService.startSession(event.exercise);
            showPuzzlePane(session);
        } catch (InvalidPuzzleConfigException e) {
            // TODO message
            // DialogPane.
        }
    }

    private void showPuzzlePane(final Session<?> session) {
        var puzzlePane = buildPuzzlePane(session);
        showAsContent(puzzlePane);
    }

    // TODO it definitely must be somewhere else, not here. though, it mustn't be encapsulated inside configPage.
    // app-level delivering mechanism
    private void updateConfigProperty(final ConfigPropertyUpdatingEvent event) {
        // TODO can app service be skipped here so that the infrastructure service is used here instead?
        var puzzleConfigService = DI.get(PuzzleConfigService.class);
        puzzleConfigService.updateProperty(event.exercise, event.configProperty, event.newValue);
    }

    private void openConfigPaneOver(final ExerciseStartedOverEvent<?> e) {
        exercisesMenu.fireExercise(e.puzzleConfigDto.exercise);
    }

    private Pane buildPuzzlePane(final Session<? extends PuzzleConfigDTO<?>> session) {
        var puzzlePane = PuzzlePanesFactory.create(session, WIDTH, HEIGHT);

        puzzlePane.addEventHandler(ExerciseFinishedEvent.EXERCISE_FINISHED, this::openExerciseFinish);

        return puzzlePane;
    }

    private void openExerciseFinish(final ExerciseFinishedEvent e) {
        showExerciseFinishPane(e.session);
        // TODO actually exerciseFinishingService.finish(e.session) should be here
        closeSession(e.session);
    }

    private void showExerciseFinishPane(final Session<?> session) {
        var sessionStatsPane = buildSessionStatsPane(session);
        showAsContent(sessionStatsPane);
    }

    private void closeSession(Session<?> session) {
        // TODO delegate to service
        session = null;
    }

    private Pane buildSessionStatsPane(final Session<?> session) {
        var sessionStatsPane = StatsPanesFactory.create(session);
        sessionStatsPane.addEventHandler(ExerciseStartedOverEvent.EXERCISE_STARTED_OVER, this::openConfigPaneOver);
        return sessionStatsPane;
    }
}
