package com.teacore.opengl;

import org.lwjgl.opengl.GL11;

/**
 * Resembles different filters that determine how a {@link OGLTexture} is resized.
 * 
 * @author ShietStone
 */
public enum OGLResizeFilter {

	NEAREST(GL11.GL_NEAREST),
	LINEAR(GL11.GL_LINEAR);
	
	private int handle;
	
	private OGLResizeFilter(int handle) {
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
