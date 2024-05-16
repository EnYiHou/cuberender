package computer;

import computer.graphic.Cube;

/**
 * The Simulation class represents the world in which the cubes are placed.
 * The world is a 3D grid of cubes.
 */
public class Simulation {

    // Settings
    public static int CUBE_SIZE = 50;
    public static int WORLD_SIZE =  1;

    // The world
    public Cube[][][] world;

    /**
     * Create a new simulation.
     */
    public Simulation() {
        int start_x = -WORLD_SIZE / 2;
        int start_y = -WORLD_SIZE / 2;

        Cube[][][] world = new Cube[WORLD_SIZE][WORLD_SIZE][WORLD_SIZE];

        for (int x = 0; x < WORLD_SIZE; x++) {
            for (int y = 0; y < WORLD_SIZE; y++) {
                for (int z = 0; z < WORLD_SIZE; z++) {
                    world[x][y][z] = new Cube(new double[] { start_x + x * CUBE_SIZE, start_y + y * CUBE_SIZE, z * CUBE_SIZE}, CUBE_SIZE);
                }
            }
        }

        this.world = world;

    }


    public Cube[][][] getWorld() {
        return world;
    }
    
}
