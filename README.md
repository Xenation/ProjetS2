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

### TODO (short term)
* Rendering Flexibility
	* currently using fixed pipeline for rendering which is heavier on the CPU and not flexible at all
	* should use programmable pipeline which allow to do more calculations on GPU and to create more complex visual effects
* Layers
* Shader Programs
