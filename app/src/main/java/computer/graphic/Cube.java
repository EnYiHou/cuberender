package computer.graphic;

public class Cube {

    // The center of the cube
    private double[] center = { 0, 0, 0 };

    // The size of the cube
    private double size = 1;

    // The array of edges of the cube, each edge is represented by two points in 3D space
    private double[][][] edges;

    public Cube() {
        setCenter();
    }

    public Cube(double[] center, double size) {
        this.center = center;
        this.size = size;
        setCenter();
    }

    private void setCenter() {

        double x = center[0];
        double y = center[1];
        double z = center[2];

        double halfSize = size / 2;
        
        this.edges = new double[][][]{
            // front bottom left to front bottom right
            { { x - halfSize, y - halfSize, z - halfSize }, { x + halfSize, y - halfSize, z - halfSize } },
            // front bottom right to front top right
            { { x + halfSize, y - halfSize, z - halfSize }, { x + halfSize, y + halfSize, z - halfSize } },
            // front top right to front top left
            { { x + halfSize, y + halfSize, z - halfSize }, { x - halfSize, y + halfSize, z - halfSize } },
            // front top left to front bottom left
            { { x - halfSize, y + halfSize, z - halfSize }, { x - halfSize, y - halfSize, z - halfSize } },
            // back bottom left to back bottom right
            { { x - halfSize, y - halfSize, z + halfSize }, { x + halfSize, y - halfSize, z + halfSize } },
            // back bottom right to back top right
            { { x + halfSize, y - halfSize, z + halfSize }, { x + halfSize, y + halfSize, z + halfSize } },
            // back top right to back top left
            { { x + halfSize, y + halfSize, z + halfSize }, { x - halfSize, y + halfSize, z + halfSize } },
            // back top left to back bottom left
            { { x - halfSize, y + halfSize, z + halfSize }, { x - halfSize, y - halfSize, z + halfSize } },
            // front bottom left to back bottom left
            { { x - halfSize, y - halfSize, z - halfSize }, { x - halfSize, y - halfSize, z + halfSize } },
            // front bottom right to back bottom right
            { { x + halfSize, y - halfSize, z - halfSize }, { x + halfSize, y - halfSize, z + halfSize } },
            // front top right to back top right
            { { x + halfSize, y + halfSize, z - halfSize }, { x + halfSize, y + halfSize, z + halfSize } },
            // front top left to back top left
            { { x - halfSize, y + halfSize, z - halfSize }, { x - halfSize, y + halfSize, z + halfSize } }
        };

    }

    public double[][][] getEdges() {
        return edges;
    }

    public void setEdges(double[][][] edges) {
        this.edges = edges;
    }

    
}