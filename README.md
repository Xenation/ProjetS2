# S2 Project

### DONE
* Basic Renderer
	* renders Sprites individually
	* renders game world's chunks
* Loader (not much to add in the future)
	* loads position/textureUVs
	* loads textures
* Basic DisplayManager
	* best displaymode available
	* does not support resizing
	* keeps track of time (delta time)
* Basic Sprites
	* Sprite
	* TileSprite
* Tiles
* ChunkMap
	* orders chunks by position to only render chunks in view (to implement)
* Chunks
	* orders tiles by sprites to optimise rendering 
* Shader Programs
	* programmable pipeline

### TODO (short term)
* Rendering Flexibility (WIP)
	* currently using fixed pipeline for rendering which is heavier on the CPU and not flexible at all (done)
	* should use programmable pipeline which allow to do more calculations on GPU and to create more complex visual effects (done)
	* currently using one VAO for each Sprite, this VAO contains only one VBO. Should regroup every Tiles of a Chunk under the same VAO and VBO. Textures should be regrouped under a same atlas to allow this (UVs under a same VBO refers to a single texture).
* Layers
