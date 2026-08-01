package org.swetlokognatsk.earking_out.app.desktop;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.swetlokognatsk.earking_out.app.desktop.components.ExercisesMenu;
import org.swetlokognatsk.earking_out.app.desktop.events.configs.ConfigPropertyUpdatingEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedOverEvent;
import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.ConfigPanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PuzzlePanesFactory;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.StatsPanesFactory;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandlers;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.Constants;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.PuzzleConfigService;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.exceptions.InvalidPuzzleConfigException;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// TODO google examples when nested classes and static nested classes are indeed a good design solution

@SpringBootApplication(scanBasePackages = { "org.swetlokognatsk.earking_out.app.desktop", "org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring" })
public class EarkingOutApplication extends Application {
    public static final int LABEL_FIELD_SPACING = 10;

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 700;

    private final BorderPane contentPane;
    private final ExercisesMenu exercisesMenu;
    private final Scene mainScene;

    public static void main(String[] args) {
        // TODO bootstrap refactoring
        var context = runSpringApp(args);
        initDI(context);
        DomainEventHandlers.registerDomainEventHandlers();
        launch();
    }

    protected static void initDI(final ApplicationContext context) {
        // TODO wash away this hack after setting up the spring boot
        DI.mode = DI.APP_MODE;
        DI.setContext(context);
    }

    protected static ApplicationContext runSpringApp(String[] args) {
        var springApplication = new SpringApplication(EarkingOutApplication.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        return springApplication.run(args);
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
        primaryStage.setTitle(Constants.APP_NAME);
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
        // TODO is it fine to pull it from DI here?
        var puzzleConfigDTOAssembler = DI.get(PuzzleConfigDTOAssembler.class);
        var puzzleConfigDto = puzzleConfigDTOAssembler.getPuzzleConfigDTO(exercise);

        var configPane = ConfigPanesFactory.create(puzzleConfigDto, WIDTH, HEIGHT);
        configPane.addEventHandler(ExerciseStartedEvent.EXERCISE_STARTED, this::tryOpenPuzzlePane);
        configPane.addEventHandler(ConfigPropertyUpdatingEvent.CONFIG_PROPERTY_UPDATING, this::updateConfigProperty);
        return (CP) configPane;
    }

    private void tryOpenPuzzlePane(final ExerciseStartedEvent<?> event) {
        var sessionService = getSessionService(event.exercise);
        try {
            var sessionId = sessionService.start();
            showPuzzlePane(sessionId);
        } catch (InvalidPuzzleConfigException e) {
            // TODO message
            // DialogPane.
        }
    }

    protected <E extends Exercise> SessionService<E, ?, ?> getSessionService(final E exercise) {
        var sessionService = switch (exercise) {
        case AudioPerfectPitchExercise e -> DI.get(AudioPerfectPitchSessionService.class);
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };
        return (SessionService<E, ?, ?>) sessionService;
    }

    private void showPuzzlePane(final SessionId sessionId) {
        var puzzlePane = buildPuzzlePane(sessionId);
        showAsContent(puzzlePane);
    }

    // TODO it definitely must be somewhere else, not here. though, it mustn't be encapsulated inside configPage.
    // TODO is `delivering mechanism` correct/existing term here?
    // app-level delivering mechanism
    private void updateConfigProperty(final ConfigPropertyUpdatingEvent event) {
        var puzzleConfigService = DI.get(PuzzleConfigService.class);
        puzzleConfigService.updateProperty(event.exercise, event.configProperty, event.newValue);
    }

    private void openConfigPaneOver(final ExerciseStartedOverEvent<?> e) {
        exercisesMenu.fireExercise(e.puzzleConfigDto.exercise);
    }

    private Pane buildPuzzlePane(final SessionId sessionId) {
        var puzzlePanesFactory = DI.get(PuzzlePanesFactory.class);
        var puzzlePane = puzzlePanesFactory.create(sessionId, WIDTH, HEIGHT);

        puzzlePane.addEventHandler(ExerciseFinishedEvent.EXERCISE_FINISHED, this::openExerciseFinish);

        return puzzlePane;
    }

    private void openExerciseFinish(final ExerciseFinishedEvent<?> e) {
        showExerciseFinishPane(e.sessionId);
    }

    private void showExerciseFinishPane(final SessionId sessionId) {
        var sessionStatsPane = buildSessionStatsPane(sessionId);
        showAsContent(sessionStatsPane);
    }

    private Pane buildSessionStatsPane(final SessionId sessionId) {
        var statsPanesFactory = DI.get(StatsPanesFactory.class);
        var sessionStatsPane = statsPanesFactory.create(sessionId);

        sessionStatsPane.addEventHandler(ExerciseStartedOverEvent.EXERCISE_STARTED_OVER, this::openConfigPaneOver);
        return sessionStatsPane;
    }
}
