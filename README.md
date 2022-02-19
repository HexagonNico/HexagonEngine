
# Hexagon Engine

Hexagon Engine is an open source game engine using LWJGL 3.

The development is currently in a very early stage and
the fate of the project is unknown.

## Design principles

Hexagon Engine follows the **Entity-Component-System** paradigm.

Everything in the game is an entity.
Entities may have many components, which are objects that store data about the entities.
Components are however not stored in entities, entities are nothing but numeric ids,
all components are stored in a table for quicker and easier access.
Systems run a specific job on all entities that have the required components,
they run once per frame and process all entities.

## TODO list

* Editor program
* Tilemaps
* Mesh rendering that renders a set of vertices instead of a model
* Mipmapping
* Physics and collision detection
* GUI
* Mouse picking
* Normal mapping
* Font rendering
* Particle system
* Shadow mapping
* Post processing effects
* Rendering targets
* Geometry shaders
* Audio

## Usage and contribution

Despite the engine not being yet in a usable state,
one could still try to make a game using it.

Any contribution to the development of the project is always appreciated.

## License

Apache License 2.0

Note: the license may be changed in the course of the development.