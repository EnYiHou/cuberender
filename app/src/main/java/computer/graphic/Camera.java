package computer.graphic;

public class Camera {

    // Singleton pattern
    public static final Camera INSTANCE = new Camera();

    // Camera settings
    public static double CAMERA_SPEED = 0.5;
    public static double ROTATION_SPEED = 0.01;
    public static double FOCAL_LENGTH = 200;

    private double[] position = { 0, 0, 100 };
    private double[] surface_normal = { 0, 0, -1};
    private double[] up_vector = { 0, 1, 0 };

    // Camera mobility functions
    public void moveForward() {
        position[0] += CAMERA_SPEED * surface_normal[0];
        position[1] += CAMERA_SPEED * surface_normal[1];
        position[2] += CAMERA_SPEED * surface_normal[2];
    }

    public void moveBackward() {
        position[0] -= CAMERA_SPEED * surface_normal[0];
        position[1] -= CAMERA_SPEED * surface_normal[1];
        position[2] -= CAMERA_SPEED * surface_normal[2];
    }

    public void moveLeft() {
        double[] left_vector = util.crossProduct(up_vector, surface_normal);
        position[0] += CAMERA_SPEED * left_vector[0];
        position[1] += CAMERA_SPEED * left_vector[1];
        position[2] += CAMERA_SPEED * left_vector[2];
    }

    public void moveRight() {
        double[] left_vector = util.crossProduct(up_vector, surface_normal);
        position[0] -= CAMERA_SPEED * left_vector[0];
        position[1] -= CAMERA_SPEED * left_vector[1];
        position[2] -= CAMERA_SPEED * left_vector[2];
    }

    public void moveUp() {
        position[0] += CAMERA_SPEED * up_vector[0];
        position[1] += CAMERA_SPEED * up_vector[1];
        position[2] += CAMERA_SPEED * up_vector[2];
    }

    public void moveDown() {
        position[0] -= CAMERA_SPEED * up_vector[0];
        position[1] -= CAMERA_SPEED * up_vector[1];
        position[2] -= CAMERA_SPEED * up_vector[2];
    }

    public void rotateLeft() {
        double[] left_vector = util.crossProduct(up_vector, surface_normal);
        double[] component1 = util.scalarProduct(surface_normal, Math.cos(ROTATION_SPEED));
        double[] component2 = util.scalarProduct(left_vector, Math.sin(ROTATION_SPEED));

        surface_normal = util.vectorAddition(component1, component2);
    }

    public void rotateRight() {
        double[] left_vector = util.crossProduct(up_vector, surface_normal);
        double[] component1 = util.scalarProduct(surface_normal, Math.cos(-ROTATION_SPEED));
        double[] component2 = util.scalarProduct(left_vector, Math.sin(-ROTATION_SPEED));

        surface_normal = util.vectorAddition(component1, component2);
    }

    public void rotateDown() {
        double[] normal_component1 = util.scalarProduct(surface_normal, Math.cos(-ROTATION_SPEED));
        double[] normal_component2 = util.scalarProduct(up_vector, Math.sin(-ROTATION_SPEED));

        double[] up_component1 = util.scalarProduct(up_vector, Math.cos(-ROTATION_SPEED));
        double[] up_component2 = util.scalarProduct(surface_normal, Math.cos(Math.PI / 2 - ROTATION_SPEED));

        surface_normal = util.vectorAddition(normal_component1, normal_component2);
        up_vector = util.vectorAddition(up_component1, up_component2);
    }

    public void rotateUp() {
        double[] normal_component1 = util.scalarProduct(surface_normal, Math.cos(ROTATION_SPEED));
        double[] normal_component2 = util.scalarProduct(up_vector, Math.sin(ROTATION_SPEED));

        double[] up_component1 = util.scalarProduct(up_vector, Math.cos(ROTATION_SPEED));
        double[] up_component2 = util.scalarProduct(surface_normal, Math.cos(Math.PI / 2 + ROTATION_SPEED));

        surface_normal = util.vectorAddition(normal_component1, normal_component2);
        up_vector = util.vectorAddition(up_component1, up_component2);
    }


    // Getters and setters
    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double[] getSurface_normal() {
        return this.surface_normal;
    }

    public void setSurface_normal(double[] surface_normal) {
        this.surface_normal = surface_normal;
    }

    public double[] getUp_vector() {
        return this.up_vector;
    }

    public void setUp_vector(double[] up_vector) {
        this.up_vector = up_vector;
    }

}
