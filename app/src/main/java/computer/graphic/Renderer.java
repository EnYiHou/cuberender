package computer.graphic;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * The Renderer class is responsible for rendering the 3D objects on the screen.
 * It contains methods to draw lines and cubes on the screen.
 */
public class Renderer {
    
    /**
     * The stroke width of the lines drawn by the renderer.
     */
    public static double STROKE_WIDTH = 2;

    /**
     * The color of the lines drawn by the renderer.
     */
    public static Paint STROKE_COLOR = Color.RED;


    /**
     * Draw a line on the screen. This method generates a series of pixels to represent the line. 
     * This method is significantly slower than the drawLineCentered2 method, 
     * as it generates each pixel individually.
     * 
     * @param x1 the x-coordinate of the starting point of the line
     * @param y1 the y-coordinate of the starting point of the line
     * @param x2 the x-coordinate of the ending point of the line
     * @param y2 the y-coordinate of the ending point of the line
     * @return an ArrayList of Rectangles representing the pixels of the line
     */
    public static ArrayList<Rectangle> drawLineCentered(double x1, double y1, double x2, double y2) {
        double width = App.primaryStage.getWidth();
        double height = App.primaryStage.getHeight();

        return drawLine(x1 + width / 2, -y1 + height / 2, x2 + width / 2, -y2 + height / 2);
    }


    /**
     * Draw a line on the screen. This method generates a javafx Line object to represent the line.
     * This method is significantly faster than the drawLineCentered method, 
     * as it generates the line as a single object.
     * 
     * @param x1 the x-coordinate of the starting point of the line
     * @param y1 the y-coordinate of the starting point of the line
     * @param x2 the x-coordinate of the ending point of the line
     * @param y2 the y-coordinate of the ending point of the line
     * @return the Line object representing the line
     */
    public static Line drawLineCentered2(double x1, double y1, double x2, double y2) {
        
        double width = App.primaryStage.getWidth();
        double height = App.primaryStage.getHeight();
        Line line = new Line(x1 + width / 2, -y1 + height / 2, x2 + width / 2, -y2 + height / 2);
        line.setStrokeWidth(STROKE_WIDTH);
        line.setStroke(STROKE_COLOR);
        App.pane.getChildren().add(line);

        return line;
    }

    /**
     * Draw a line on the screen with a specified stroke width.
     * 
     * @param x1 the x-coordinate of the starting point of the line
     * @param y1 the y-coordinate of the starting point of the line
     * @param x2 the x-coordinate of the ending point of the line
     * @param y2 the y-coordinate of the ending point of the line
     * @param stroke_width the width of the line
     * @return the Line object representing the line
     */
    public static Line drawLineCentered2(double x1, double y1, double x2, double y2, Pane pane, double stroke_width) {
        Line line = drawLineCentered2(x1, y1, x2, y2);
        line.setStrokeWidth(stroke_width);
        return line;
    }

    /**
     * Draw a line on the screen. This method generates a series of pixels to represent the line.
     * Note that this method is significantly slow and should be used with caution.
     * @param x1 the x-coordinate of the starting point of the line
     * @param y1 the y-coordinate of the starting point of the line 
     * @param x2  the x-coordinate of the ending point of the line
     * @param y2 the y-coordinate of the ending point of the line
     * @return an ArrayList of Rectangles representing the pixels of the line
     */
    public static ArrayList<Rectangle> drawLine(double x1, double y1, double x2, double y2) {
        ArrayList<Rectangle> pixels = new ArrayList<Rectangle>();
        double slope = (y2 - y1) / (x2 - x1);

        // If the slope is shallow, we iterate over the x-axis
        if (Math.abs(slope) <= 1) {

            double[] point1 = new double[] { x1, y1 };
            double[] point2 = new double[] { x2, y2 };
            // If the line is going from right to left, we swap the points
            if (x1 > x2) {
                point1 = new double[] { x2, y2 };
                point2 = new double[] { x1, y1 };
            }
            double x = Math.max(point1[0], 0);
            while (x <= point2[0]) {
                Rectangle pixel = new Rectangle(STROKE_WIDTH, STROKE_WIDTH, STROKE_COLOR);
                pixel.setLayoutX(x - (STROKE_WIDTH / 2.0));
                pixel.setLayoutY(slope * (x - point1[0]) + point1[1] - (STROKE_WIDTH / 2.0));
                App.pane.getChildren().add(pixel);
                pixels.add(pixel);
                x += 1;
                if (x > App.primaryStage.getWidth()) {
                    break;
                }
            }
        }
        // If the slope is steep, we iterate over the y-axis
        else {

            double[] point1 = new double[] { x1, y1 };
            double[] point2 = new double[] { x2, y2 };

            // If the line is going from bottom to top, we swap the points
            if (y1 > y2) {
                point1 = new double[] { x2, y2 };
                point2 = new double[] { x1, y1 };
            }

            double y = Math.max(point1[1], 0);
            while (y <= point2[1]) {
                Rectangle pixel = new Rectangle(STROKE_WIDTH, STROKE_WIDTH, STROKE_COLOR);
                pixel.setLayoutX((y - point1[1]) / slope + point1[0] - (STROKE_WIDTH / 2.0));
                pixel.setLayoutY(y);
                App.pane.getChildren().add(pixel);
                pixels.add(pixel);
                y += 1;
                if (y > App.primaryStage.getHeight()) {
                    break;
                }
            }

        }
        return pixels;
    }


    /**
     * Given a cube, this method draws the cube on the screen.
     * The cube is drawn in 3D space and is projected onto a 2D plane.
     * 
     * @param cube the cube to draw
     * @return an ArrayList of Lines representing the edges of the cube
     */
    public static ArrayList<Line> drawCube(Cube cube) {
        ArrayList<Line> pixels = new ArrayList<Line>();

        double[][][] edges = cube.getEdges();

        for (int i = 0; i < edges.length; i++) {
            double[][] edge = edges[i];
            double[] vertex1 = util.vectorSubtraction(edge[0], Camera.INSTANCE.getPosition());
            double[] vertex2 = util.vectorSubtraction(edge[1], Camera.INSTANCE.getPosition());

            double[] starting = null;
            double[] ending = null;

            // 3 possibilities
            // 1 - Both vertices are in front of the camera
            // 2 - Both vertices are behind the camera
            // 3 - One vertex is in front of the camera and the other is behind the camera
            double dot1 = util.dotProduct(vertex1, Camera.INSTANCE.getSurface_normal());
            double dot2 = util.dotProduct(vertex2, Camera.INSTANCE.getSurface_normal());

            // 1 - Both vertices are in front of the camera
            if (dot1 > 0 && dot2 > 0) {
                starting = project(vertex1, Camera.INSTANCE);
                ending = project(vertex2, Camera.INSTANCE);
            }
            // 2 - Both vertices are behind the camera
            else if (dot1 < 0 && dot2 < 0) {
                continue;
            }

            // 3 - One vertex is in front of the camera and the other is behind the camera
            else {
                // When vertex1 is in front
                if (dot1 > 0) {
                    starting = project(vertex1, Camera.INSTANCE);
                }
                // When vertex2 is in front
                else {
                    starting = project(vertex2, Camera.INSTANCE);
                }
                // Plane : Ax + By + Cz = D
                // Given surface_normal = [A, B, C], and position = [x, y, z] we find D
                double D = Camera.INSTANCE.getSurface_normal()[0] * Camera.INSTANCE.getPosition()[0]
                        + Camera.INSTANCE.getSurface_normal()[1] * Camera.INSTANCE.getPosition()[1]
                        + Camera.INSTANCE.getSurface_normal()[2] * Camera.INSTANCE.getPosition()[2];
                
                // Construct the plane Ax + By + Cz = D
                double[] plane = new double[] { Camera.INSTANCE.getSurface_normal()[0],
                        Camera.INSTANCE.getSurface_normal()[1],
                        Camera.INSTANCE.getSurface_normal()[2], D };

                // Find the intersection of the line and the plane
                double[] intersection = util.linePlaneIntersection(plane, edge[0], edge[1]);
                if (intersection == null) {
                    continue;
                }

                // Translate the intersection to the camera's position
                intersection = util.vectorSubtraction(intersection, Camera.INSTANCE.getPosition());

                // Find the x and y components of the intersection in the camera's frame of reference
                double[] right_vector = util.crossProduct(Camera.INSTANCE.getSurface_normal(),
                        Camera.INSTANCE.getUp_vector());
                double x_component = util.dotProduct(intersection, right_vector) * 10000;
                double y_component = util.dotProduct(intersection, Camera.INSTANCE.getUp_vector()) * 10000;

                ending = new double[] { x_component, y_component };
                

            }
            pixels.add(drawLineCentered2(starting[0], starting[1], ending[0], ending[1]));
        }
        return pixels;
    }

    

    private static double[] project(double[] vertex, Camera camera) {
        if (vertex.length != 3) {
            throw new IllegalArgumentException("Vertex must have 3 coordinates");
        }
        double[] projected = new double[2];

        // Plane = Ax + By + Cz = D
        // Given surface_normal = [A, B, C], and position = [x, y, z] we find D
        double[] position = camera.getPosition();
        double[] surface_normal = camera.getSurface_normal();
        double D = surface_normal[0] * position[0] + surface_normal[1] * position[1] + surface_normal[2] * position[2];
        double[] projected_vertex = new double[2];

        projected_vertex = util
                .pointProjection(new double[] { surface_normal[0], surface_normal[1], surface_normal[2], D }, vertex);
        double[] right_vector = util.crossProduct(camera.getSurface_normal(), camera.getUp_vector());
        double[] up_vector = camera.getUp_vector();

        double x = util.dotProduct(projected_vertex, right_vector);
        double y = util.dotProduct(projected_vertex, up_vector);

        // create perspective
        double z = util.dotProduct(vertex, camera.getSurface_normal());
        double distance = Camera.FOCAL_LENGTH;
        projected[0] = (distance * x) / (z);
        projected[1] = (distance * y) / (z);

        return projected;
    }

}
