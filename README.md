
# Hexagon Engine

Hexagon Engine is an open source game engine using LWJGL 3.

The development is currently in a very early stage and
the fate of the project is unknown.

## Design principles

Hexagon Engine follows the **Entity-Component-System** paradigm.

Everything in the game is an entity.
Entities may have many components, which are objects that store data about the entities.
Components are however not stored in entities, entities are nothing but references,
all components are stored in a table for quicker and easier access.
Systems run a specific job on all entities that have the required components,
they run once per frame and process all entities.

## TODO list

* Write tutorials
* Editor program
* Tilemaps
* Rendering of 3D meshes (from a set of vertices and indices)
* Rendering of 3D models (.obj...)
* Texture import parameters (filter, mip-map)
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
