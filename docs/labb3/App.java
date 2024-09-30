package se.kth.adamfhhugoper.labb3;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import se.kth.adamfhhugoper.labb3.Shapes.*;

public class App extends Application {

    private World world;

    private Canvas canvas; // the surface whera pad and balls are drawn
    private AnimationTimer timer;

    protected class BounceTimer extends AnimationTimer {

        private long previousNs = 0;

        /**
         * This method deals with drawing the world. You do not have to change
         * this code, except for handling "game over" (see comment below).
         *
         * @param nowNs
         */
        @Override
        public void handle(long nowNs) {
            // odd initialization...
            if (previousNs == 0) {
                previousNs = nowNs;
            }

            // the canvas might have been reshaped, e.g. by user interaction;
            // resize the world
            world.setDimensions(canvas.getWidth(), canvas.getHeight());

            // move the objects in the world
            world.moveAndConstrain(nowNs - previousNs); // elapsed time
            // save the new timestamp, for the next cycle
            previousNs = nowNs;

            GraphicsContext gc = canvas.getGraphicsContext2D();

            // paint the background
            gc.setFill(Color.WHITESMOKE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            // paint the shapes
            for (Shape b : world.getShapes()) {
                b.paint(gc);
            }
        }
    }

    // This code initializes the graphics. You do not have to (should not)
    // change this code.
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300, Color.WHITESMOKE);

        canvas = new Canvas();
        // automatically resize the canvas when the stage/scene is resized
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());
        root.getChildren().add(canvas);

        stage.setTitle("Shapes");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.sizeToScene();
        stage.show();

        world = new World(canvas.getWidth(), canvas.getHeight());

        timer = new BounceTimer();
        timer.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void showAlert(String message) {
        alert.setHeaderText("");
        alert.setTitle("Alert!");
        alert.setContentText(message);
        alert.show();
    }

    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
}