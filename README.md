# CubeRenderer

CubeRenderer is a personal project built with JavaFX, where I explored and implemented various linear algebra concepts and computer graphics techniques. The primary focus of this project was to render a 3D cube in a virtual world with a freely movable camera.


## Usage

To use the CubeRenderer project, follow these steps:

1. Clone the repository:
    ```bash
    git clone https://github.com/EnYiHou/cuberender
    ```

2. Navigate to the project directory:
    ```bash
    cd cuberender/
    ```

3. Run the project using Gradle:
    ```bash
    ./gradlew run
    ```


## Features

- **3D Cube Rendering**: The project renders a 3D cube in a virtual 3D world using linear algebra and computer graphics principles.
- **Free Camera Movement**: The camera can be moved freely within the 3D world using the WASD keys for translation and arrow keys for controlling the view angle.
- **Perspective Projection**: The cube is rendered using a perspective projection, providing a realistic 3D viewing experience.
- **Camera Inside Cube Handling**: The project handles scenarios where the camera is positioned inside the cube, ensuring correct rendering and visibility.

## Techniques Explored


- **Linear Algebra**: Extensive use of linear algebra concepts, such as vectors, matrices, and transformations, to handle 3D rendering and camera movement.
- **Rasterization**: Although not actively used due to performance considerations, the project includes code for rendering individual pixels through rasterization.
- **Perspective Projection**: Implementation of perspective projection to achieve a realistic 3D viewing experience.
- **Camera Handling**: Techniques for handling camera movement, including translation and rotation, within the 3D world.

## Purpose

This project was developed solely for personal enjoyment and as a means to apply and reinforce my understanding of linear algebra concepts in a practical, real-life application of computer graphics rendering. 
