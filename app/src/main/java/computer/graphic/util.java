package computer.graphic;


/**
 * A utility class that contains useful functions for vector operations.
 */
public class util {

    public static double[] crossProduct(double[] a, double[] b) {
        double[] result = new double[3];
        result[0] = a[1] * b[2] - a[2] * b[1];
        result[1] = a[2] * b[0] - a[0] * b[2];
        result[2] = a[0] * b[1] - a[1] * b[0];
        return result;
    }

    public static double[] scalarProduct(double[] a, double scalar) {
        double[] result = new double[3];
        result[0] = a[0] * scalar;
        result[1] = a[1] * scalar;
        result[2] = a[2] * scalar;
        return result;
    }

    public static double[] vectorAddition(double[] a, double[] b) {
        double[] result = new double[3];
        result[0] = a[0] + b[0];
        result[1] = a[1] + b[1];
        result[2] = a[2] + b[2];
        return result;
    }

    public static double[] vectorSubtraction(double[] a, double[] b) {
        double[] result = new double[3];
        result[0] = a[0] - b[0];
        result[1] = a[1] - b[1];
        result[2] = a[2] - b[2];
        return result;
    }

    public static double dotProduct(double[] a, double[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    /**
     * Calculate the projection of a point on a plane.
     * However, if the point is on the opposite side of the normal vector, then the
     * function will throw an exception.
     * 
     * @param plane the plane represented by a 4D vector [A, B, C, D] where Ax + By + Cz = D
     * @param point the point in 3D space to project on the plane
     * @return the projection of the point on the plane
     */
    public static double[] pointProjection(double[] plane, double[] point) {
        double[] result = new double[3];
        double[] plane_normal = new double[3];
        plane_normal[0] = plane[0];
        plane_normal[1] = plane[1];
        plane_normal[2] = plane[2];
        // Ax + By + Cz = D

        // A * (x + c*v1) + B * (y + c*v2) + C * (z + c*v3) = D
        // Ax + Acv1 + By + Bcv2 + Cz + Ccv3 = D
        // c (Av1 + Bv2 + Cv3) = D - A*x - B*y - C*z
        // c = (D - A*x - B*y - C*z) / (Av1 + Bv2 + Cv3)
        double c = (plane[3] - plane[0] * point[0] - plane[1] * point[1] - plane[2] * point[2])
                / dotProduct(plane_normal, point);

        result[0] = point[0] + c * plane_normal[0];
        result[1] = point[1] + c * plane_normal[1];
        result[2] = point[2] + c * plane_normal[2];
        return result;
    }

    /**
     * Calculate the intersection of a line and a plane.
     * 
     * @param plane a plane represented by a 4D vector [A, B, C, D] where Ax + By + Cz = D
     * @param vertex1 the first vertex of the line in 3D space
     * @param vertex2 the second vertex of the line in 3D space
     * @return the intersection point of the line and the plane or null if there is no intersection
     * 
     */
    public static double[] linePlaneIntersection(double[] plane, double[] vertex1, double[] vertex2) {
        double[] vector = vectorSubtraction(vertex2, vertex1);
        // Plane : Ax + By + Cz = D
        // Vector : [v1, v2, v3] = [x2 - x1, y2 - y1, z2 - z1]
        // Line = vertex1 + t * vector = [x1 + t * v1, y1 + t * v2, z1 + t * v3]
        // vertex1 + t * vector = plane
        // A(x1 + t * v1) + B(y1 + t * v2) + C(z1 + t * v3) = D
        // Ax1 + Atv1 + By1 + Btv2 + Cz1 + Ctv3 = D
        // t(Av1 + Bv2 + Cv3) = D - Ax1 - By1 - Cz1
        // t = (D - Ax1 - By1 - Cz1) / (Av1 + Bv2 + Cv3)

        double denominator = dotProduct(plane, vector);
        double t = (plane[3] - dotProduct(plane, vertex1)) / denominator;

        if (t < 0 || t > 1 || denominator == 0) {
            return null;
        }

        double[] intersection = new double[3];
        intersection[0] = vertex1[0] + t * vector[0];
        intersection[1] = vertex1[1] + t * vector[1];
        intersection[2] = vertex1[2] + t * vector[2];

        return intersection;
    }

}
