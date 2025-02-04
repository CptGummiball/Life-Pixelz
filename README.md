# LifePixelz Simulation

LifePixelz is a simple simulation that replicates the behavior of pixels in a grid with resources and storage buildings. The pixels interact with resources, construct buildings, and engage in combat with each other. This simulation uses JavaFX to visually represent the interactions.

**Note:** This project is a **Work in Progress (WIP)**. There are still many areas for improvement and known issues that need to be addressed.

Additionally, the code currently contains unused methods that are remnants of the original version. These methods may still be relevant for future versions.

## Features

- **Pixels**: Small entities that live in the grid, move around, collect resources, construct buildings, and engage in combat.
- **Resources**: Mineable fields that regenerate over time after being depleted.
- **Buildings (Storage)**: Pixels can construct buildings in designated areas to store resources.
- **Combat**: Pixels can fight each other, losing energy in the process.
- **Log**: All pixel actions are recorded in a log that tracks the simulation state.
- **Resource Regeneration**: Resources regenerate over time with a certain probability and remain in the grid after depletion for future use.

## Functionality

1. When the program starts, a grid is displayed where pixels reside.
2. The pixels move randomly and interact with resources, storage buildings, and other pixels.
3. The log window displays all major actions performed by pixels, such as resource collection, building construction, and combat.
4. Resources regenerate over time but only if they have been depleted. They remain in the grid and can be reused.

## Architecture

### Classes:

1. **LifePixelz (Main Class)**: Starts the application and manages the simulation.
2. **Grid**: Represents the grid containing the cells where pixels live. It includes methods for resource generation and renewal.
3. **Cell**: Represents an individual cell in the grid, which can be empty, a resource, or a building.
4. **Pixel**: Represents a pixel that moves across the grid, collects resources, engages in combat, and constructs buildings.

### Functionality:

- **Cells (Cell)** can have three states:
    - **empty**: An empty cell where a pixel can construct a building.
    - **resource**: A resource that can be mined.
    - **building**: A building (storage) used by pixels to store resources.

- **Simulation**: The simulation runs in a separate thread that updates the grid, moves pixels, and executes their actions at regular intervals.

## Example View

![LifePixelz Screenshot](https://github.com/CptGummiball/Life-Pixelz/blob/main/screenshot.PNG)

> A screenshot of the LifePixelz simulation (if you run the repository on your local machine, you will be able to see the view).

## Contributing

If you have suggestions for improvements or would like to contribute to the development, feel free to submit a pull request!

## License

This project is licensed under the MIT License - see [LICENSE](LICENSE) for details.