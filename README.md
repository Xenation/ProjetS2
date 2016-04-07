# S2 Project

### DONE
* Basic Renderer
	* only renders Sprites individually
* Loader (not much to add)
	* loads position/textureUVs
	* loads textures
* Basic DisplayManager
	* screen size: 1280x720
	* does not support resizing
	* keeps track of time (delta time)
* Basic Sprites
	* Sprite
	* TileSprite

### TODO (short term)
* Rendering Flexibility
	* currently using fixed pipeline for rendering which is heavier on the CPU and not flexible at all
	* should use programmable pipeline which allow to do more calculations on GPU and to create more complex visual effects
* Layers
* Shader Programs