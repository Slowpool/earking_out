package org.swetlokognatsk.earking_out.app.desktop;

import org.swetlokognatsk.earking_out.core.domain.model.music.Invariants;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public final class EarkingOutApplication extends Application {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    public void start(Stage primaryStage) throws Exception {
        var borderPane = new BorderPane();
        var scene = new Scene(borderPane, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setTitle(Invariants.APP_NAME);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
