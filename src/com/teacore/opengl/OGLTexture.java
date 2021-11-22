package com.teacore.opengl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.teacore.glfw.GLFWWindow;

/**
 * This class manages an OpenGL texture and provides mapping functions for the corresponding GL 
 * calls. It extends {@link OGLAllocatedData} to avoid memory leaks and (un-)registers itself 
 * automatically.
 * 
 * @author ShietStone
 */
public class OGLTexture extends OGLAllocatedData {

	private int textureHandle;
	private int width;
	private int height;
	private OGLTextureSlot lastTextureSlot;
	
	/**
	 * Creates this texture with the given image data and arguments. The forcePowerOfTwo flag 
	 * controls whether the image will be resized so its dimensions are a power of two (which GPUs
	 * can handle better and some older GPUs even require it). No argument may be null.
	 * 
	 * @param image The image data of the texture
	 * @param wrapMode What happens if texture data outside of its bounds is accessed 
	 * @param resizeFilter How the image should be interpolated when shown at different sizes
	 * @param forcePowerOfTwo If the image dimensions should be resized to a power of two
	 */
	public OGLTexture(BufferedImage image, OGLWrapMode wrapMode, OGLResizeFilter resizeFilter, boolean forcePowerOfTwo) {
		super();
		
		if(image == null || wrapMode == null || resizeFilter == null)
			throw new IllegalArgumentException("An argument is null");
		
		width = image.getWidth();
		height = image.getHeight();
		
		if(forcePowerOfTwo) {
			width = getNextPowerOfTwo(width);
			height = getNextPowerOfTwo(height);
		}
		
		BufferedImage nImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = nImage.createGraphics();
		graphics.drawImage(image, 0, 0, width, height, null);
		graphics.dispose();
	
		int[] rgbData = new int[width * height];
        image.getRGB(0, 0, width, height, rgbData, 0, width);
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(width * height * 4);
        
        for(int y = 0; y < image.getHeight(); y++)
            for(int x = 0; x < image.getWidth(); x++) {
                int pixel = rgbData[y * image.getWidth() + x];
                byteBuffer.put((byte) ((pixel >> 16) & 0xFF));
                byteBuffer.put((byte) ((pixel >> 8) & 0xFF));
                byteBuffer.put((byte) (pixel & 0xFF));
                byteBuffer.put((byte) ((pixel >> 24) & 0xFF));
            }

        byteBuffer.flip();

		textureHandle = GL11.glGenTextures();
		bind(OGLTextureSlot.T0);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrapMode.getHandle());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrapMode.getHandle());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, resizeFilter.getHandle());
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, resizeFilter.getHandle());
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, byteBuffer);
		unbind();
	}
	
	/**
	 * Binds this {@link OGLTexture} to the given {@link OGLTextureSlot}, which may not be null. An
	 * {@link IllegalStateException} will be thrown if either this object was already deleted or 
	 * the wrong OpenGL context is current.
	 * 
	 * @param slot The slot to bind this texture to
	 */
	public void bind(OGLTextureSlot slot) {
		if(slot == null)
			throw new IllegalArgumentException("Texture slot is null");
		
		if(isDeleted())
			throw new IllegalStateException("Texture already deleted");
	
        if(getContext() != GLFWWindow.getCurrentContext())
            throw new IllegalStateException("The wrong OpenGL context is current");
        
		GL20.glActiveTexture(slot.getHandle());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureHandle);
		lastTextureSlot = slot;
	}
	
	/**
	 * Unbinds any {@link OGLTexture} from the last {@link OGLTextureSlot} this texture was bound 
	 * to. There are no safety checks yet to ensure this texture is bound to that slot. Will throw 
	 * an {@link IllegalStateException} if already deleted or the wrong OpenGL context is current.
	 */
	public void unbind() {
		if(isDeleted())
			throw new IllegalStateException("Texture already deleted");
	
        if(getContext() != GLFWWindow.getCurrentContext())
            throw new IllegalStateException("The wrong OpenGL context is current");
        
		GL20.glActiveTexture(lastTextureSlot.getHandle());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Returns the textures width, which will be zero if it was deleted already.
	 * 
	 * @return The textures width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the textures height, which will be zero if it was deleted already.
	 * 
	 * @return The textures height
	 */
	public int getHeight() {
		return height;
	}
	
    /**
     * Deletes this texture and unregisters this object from OGLAllocatedData. An
     * {@link IllegalStateException} is thrown if it was already deleted or the wrong OpenGL 
     * context is current.
     */
	@Override
	public void delete() {
        if(isDeleted())
            throw new IllegalStateException("The shader program was already deleted");
        
        if(getContext() != GLFWWindow.getCurrentContext())
            throw new IllegalStateException("The wrong OpenGL context is current");
        
        super.delete();
        OGLAllocatedData.unregister(this);
        
        GL11.glDeleteTextures(textureHandle);
        width = 0;
        height = 0;
	}
	
	private int getNextPowerOfTwo(int i) {
		int counter = 2;
		
		while(counter < i)
			counter *= 2;
		
		return counter;
	}
}
