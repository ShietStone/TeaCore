package com.teacore.opengl;

import org.lwjgl.opengl.GL11;

/**
 * Resembles different modes that determine how an {@link OGLTexture} behaves when texture data 
 * out of bounds is accessed.
 * 
 * @author ShietStone
 */
public enum OGLWrapMode {

	CLAMP(GL11.GL_CLAMP),
	REPEAT(GL11.GL_REPEAT);
	
	private int handle;
	
	private OGLWrapMode(int handle) {
		this.handle = handle;
	}
	
	/**
	 * Returns the OpenGL ID of this filter.
	 * 
	 * @return The OpenGL ID
	 */
	public int getHandle() {
		return handle;
	}
}
