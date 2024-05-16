
package computer.graphic;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * 
 * To ensure a smooth camera movement, we kept track of the movement states of the camera,
 * and at each frame, we moved the camera according to the movement states. This is a better alternative than 
 * moving the camera directly when a key is pressed, as the latter would make the camera movement dependent on the
 * polling rate of the keyboard, which might cause the camera to move in a jerky manner.
 * 
 */
public class App extends Application {

    // The application's components
    public static Stage primaryStage;
    public static Pane pane;
    public static HBox header;
    public static Scene scene;

    // Initial window size
    private static int WIDTH = 800;
    private static int HEIGHT = 600;

    // Camera movement states. This is used to
    private boolean move_forward = false;
    private boolean move_backward = false;
    private boolean move_left = false;
    private boolean move_right = false;
    private boolean move_up = false;
    private boolean move_down = false;
    private boolean rotate_up = false;
    private boolean rotate_down = false;
    private boolean rotate_left = false;
    private boolean rotate_right = false;

    private Simulation simulation;

    // The main entry point for all JavaFX applications
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.setStage(primaryStage);

        // Create a new simulation
        simulation = new Simulation();

        // Handle user input
        setUserInput();

        // Create an animation timer and start the animation
        AnimationTimer timer = makeAnimationTimer();
        timer.start();
    }

    /**
     * Create an animation timer that updates the camera position and renders the
     * scene at each frame.
     * 
     * This is the main loop of the application.
     * Any changes to what is rendered on the screen should be done here.
     * 
     * @return The animation timer
     */
    private AnimationTimer makeAnimationTimer() {

        AnimationTimer timer = new AnimationTimer() {

            // A list of nodes to be rendered at each frame
            ArrayList<Node> nodes = new ArrayList<Node>();

            @Override
            public void handle(long now) {
                updateHeader();
                nodes.forEach(pixel -> pane.getChildren().remove(pixel));
                nodes.clear();
                updateCamera();
                for (Cube[][] cubes : simulation.getWorld()) {
                    for (Cube[] cube : cubes) {
                        for (Cube c : cube) {
                            nodes.addAll(Renderer.drawCube(c));
                        }
                    }
                }
                
            }
        };

        return timer;
    }

    /**
     * Update the camera position based on the movement states.
     */
    private void updateCamera() {
        if (move_forward) {
            Camera.INSTANCE.moveForward();
        }
        if (move_backward) {
            Camera.INSTANCE.moveBackward();
        }
        if (move_left) {
            Camera.INSTANCE.moveLeft();
        }
        if (move_right) {
            Camera.INSTANCE.moveRight();
        }
        if (move_up) {
            Camera.INSTANCE.moveUp();
        }
        if (move_down) {
            Camera.INSTANCE.moveDown();
        }
        if (rotate_up) {
            Camera.INSTANCE.rotateUp();
        }
        if (rotate_down) {
            Camera.INSTANCE.rotateDown();
        }
        if (rotate_left) {
            Camera.INSTANCE.rotateLeft();
        }
        if (rotate_right) {
            Camera.INSTANCE.rotateRight();
        }
    }

    /**
     * Make a structured stage with a header and a pane.
     * 
     * @param primaryStage The primary stage of the application.
     */
    private void setStage(Stage primaryStage) {
        App.primaryStage = primaryStage;
        primaryStage.setTitle("Computer Graphic");
        primaryStage.show();
        Pane pane = new Pane();
        App.pane = pane;
        HBox header = new HBox();
        App.header = header;
        VBox root = new VBox(header, pane);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        App.scene = scene;
        primaryStage.setScene(scene);
        App.primaryStage = primaryStage;
    }

    /**
     * Set the user input for the camera movement.
     * The camera can move forward, backward, left, right, up, and down using the W,
     * S, A, D, Q, and E keys.
     * The camera can also rotate up, down, left, and right using the arrow keys.
     * The camera's position, surface normal, and up vector can be printed to the
     * console.
     */
    private void setUserInput() {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    move_forward = true;
                    break;
                case S:
                    move_backward = true;
                    break;
                case A:
                    move_left = true;
                    break;
                case D:
                    move_right = true;
                    break;
                case LEFT:
                    rotate_left = true;
                    break;
                case RIGHT:
                    rotate_right = true;
                    break;
                case UP:
                    rotate_up = true;
                    break;
                case DOWN:
                    rotate_down = true;
                    break;
                case Q:
                    move_up = true;
                    break;
                case E:
                    move_down = true;
                    break;
                case P:
                    System.out.println("Camera position: " + Arrays.toString(Camera.INSTANCE.getPosition()));
                    System.out
                            .println("Camera surface normal: " + Arrays.toString(Camera.INSTANCE.getSurface_normal()));
                    System.out.println("Camera up vector: " + Arrays.toString(Camera.INSTANCE.getUp_vector()));
                    break;
                default:
                    break;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W:
                    move_forward = false;
                    break;
                case S:
                    move_backward = false;
                    break;
                case A:
                    move_left = false;
                    break;
                case D:
                    move_right = false;
                    break;
                case LEFT:
                    rotate_left = false;
                    break;
                case RIGHT:
                    rotate_right = false;
                    break;
                case UP:
                    rotate_up = false;
                    break;
                case DOWN:
                    rotate_down = false;
                    break;
                case Q:
                    move_up = false;
                    break;
                case E:
                    move_down = false;
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * Update the header with the camera position, surface normal, and up vector.
     */
    private void updateHeader() {
        header.getChildren().clear();
        header.setPrefWidth(WIDTH);
        header.setBackground(Background.EMPTY);
        VBox camera = new VBox();
        camera.getChildren().add(new Label("Camera position: " + Arrays.toString(Camera.INSTANCE.getPosition())));
        camera.getChildren()
                .add(new Label("Camera surface normal: " + Arrays.toString(Camera.INSTANCE.getSurface_normal())));
        camera.getChildren().add(new Label("Camera up vector: " + Arrays.toString(Camera.INSTANCE.getUp_vector())));
        header.getChildren().add(camera);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
