package com.teacore.glfw;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

/**
 * A utility layer to provide the LWJGL GLFW calls in a more friendly manner.
 * 
 * @author ShiteStone
 */
public class GLFWWindow {
    
    private static ArrayList<GLFWWindow> windows;
    
    static {
        windows = new ArrayList<>();
    }
    
    /**
     * Destroys all GLFWWindows that are not yet destroyed.
     */
    public static void destroyAll() {
        while(windows.size() > 0)
            windows.get(0).destroy();
    }
    
    private long windowHandle;
    private boolean destroyed;
    
    /**
     * Creates a GLFWWindow object, which is a utility layer between the program and the GLFW calls
     * provided by LWJGL. This constructor takes all possible (implemented) modifications for the
     * window into account. The window will in all instances be decorated and centered. If the 
     * creation call somehow fails or GLFW is not ready an IllegalStateException will be thrown.
     * The windows OpenGL context will be current.
     * 
     * @param title The windows title (may be null)
     * @param width The windows width, must be >= 2 and < 4096
     * @param height The windows height, must be >= 2 and < 4096
     * @param resizable If the window will be resizable by the user
     * @param vSync If the window refresh rate will be using V-Sync
     */
    public GLFWWindow(String title, int width, int height, boolean resizable, boolean vSync) {
        if(!GLFWUtil.isInitialized() || GLFWUtil.isTerminated())
            throw new IllegalStateException("GLFW is not ready (not initialized or already terminated)");
        
        if(width < 2 || height < 2 || width > 4096 || height > 4096)
            throw new IllegalArgumentException("Illegal dimensions: " + width + "x" + height);
        
        if(title == null)
            title = "";
        
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
        
        windowHandle = GLFW.glfwCreateWindow(width, height, title, 0L, 0L);
                
        if(windowHandle == 0L)
            throw new IllegalStateException("Failed to create the GLFW window");

        try(MemoryStack stack = MemoryStack.stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(windowHandle, pWidth, pHeight);

            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            
            GLFW.glfwSetWindowPos(
                    windowHandle,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        GLFW.glfwMakeContextCurrent(windowHandle);
        GLFW.glfwSwapInterval(vSync ? 1 : 0);
        GLFW.glfwShowWindow(windowHandle);
        GL.createCapabilities();
        
        windows.add(this);
        destroyed = false;
    }
    
    /**
     * Creates a GLFWWindow object, which is a utility layer between the program and the GLFW calls
     * provided by LWJGL. This constructor will make the window full screen to the provided
     * monitor. If the provided monitor is null or not valid then the systems primary monitor
     * will be used. If the creation call somehow fails or GLFW is not ready an 
     * IllegalStateException will be thrown. The windows OpenGL context will be current.
     * 
     * @param title The windows title (may be null)
     * @param vSync If the windows refresh rate will be using V-Sync
     * @param monitor The monitor to display full screen to
     */
    public GLFWWindow(String title, boolean vSync, GLFWMonitor monitor) {
        if(!GLFWUtil.isInitialized() || GLFWUtil.isTerminated())
            throw new IllegalStateException("GLFW is not ready (not initialized or already terminated)");
        
        if(title == null)
            title = "";
        
        if(monitor == null || !monitor.isValid())
            monitor = GLFWMonitor.getPrimary();
                
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(monitor.getHandle());
        
        windowHandle = GLFW.glfwCreateWindow(vidmode.width(), vidmode.height(), title, monitor.getHandle(), 0L);
                
        if(windowHandle == 0L)
            throw new RuntimeException("Failed to create the GLFW window");

        try(MemoryStack stack = MemoryStack.stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(windowHandle, pWidth, pHeight);
            
            GLFW.glfwSetWindowPos(
                    windowHandle,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        GLFW.glfwMakeContextCurrent(windowHandle);
        GLFW.glfwSwapInterval(vSync ? 1 : 0);
        GLFW.glfwShowWindow(windowHandle);
        GL.createCapabilities();
        
        windows.add(this);
        destroyed = false;
    }
    
    /**
     * Returns the cursors x position relative to the windows content. If the window was destroyed
     * an IllegalStateException will be thrown.
     * 
     * @return The cursors x position relative to the windows content
     */
    public int getCursorX() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        DoubleBuffer buffer;
        
        try(MemoryStack stack = MemoryStack.stackPush() ) {
            buffer = stack.mallocDouble(1);
            GLFW.glfwGetCursorPos(windowHandle, buffer, null);
        }
        
        return (int) buffer.get(0);
    }

    /**
     * Returns the cursors y position relative to the windows content. If the window was destroyed
     * an IllegalStateException will be thrown.
     * 
     * @return The cursors y position relative to the windows content
     */
    public int getCursorY() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        DoubleBuffer buffer;
        
        try(MemoryStack stack = MemoryStack.stackPush() ) {
            buffer = stack.mallocDouble(1);
            GLFW.glfwGetCursorPos(windowHandle, null, buffer);
        }
        
        return (int) buffer.get(0);
    }
    
    /**
     * Returns the windows x position. If the window was destroyed an IllegalStateException will 
     * be thrown.
     * 
     * @return The windows x position
     */
    public int getWindowX() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        IntBuffer buffer;
        
        try(MemoryStack stack = MemoryStack.stackPush() ) {
            buffer = stack.mallocInt(1);
            GLFW.glfwGetWindowPos(windowHandle, buffer, null);
        }
        
        return buffer.get(0);
    }
    
    /**
     * Returns the windows y position. If the window was destroyed an IllegalStateException will 
     * be thrown.
     * 
     * @return The windows y position
     */
    public int getWindowY() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        IntBuffer buffer;
        
        try(MemoryStack stack = MemoryStack.stackPush() ) {
            buffer = stack.mallocInt(1);
            GLFW.glfwGetWindowPos(windowHandle, null, buffer);
        }
        
        return buffer.get(0);
    }
    
    /**
     * Returns the windows full width. If the window was destroyed an IllegalStateException will
     * be thrown.
     * 
     * @return The windows full width
     */
    public int getWindowWidth() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        IntBuffer buffer;
        
        try(MemoryStack stack = MemoryStack.stackPush() ) {
            buffer = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(windowHandle, buffer, null);
        }
        
        return buffer.get(0);
    }
    
    /**
     * Returns the windows full height. If the window was destroyed an IllegalStateException will
     * be thrown.
     * 
     * @return The windows full height
     */
    public int getWindowHeight() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        IntBuffer buffer;
        
        try(MemoryStack stack = MemoryStack.stackPush() ) {
            buffer = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(windowHandle, null, buffer);
        }
        
        return buffer.get(0);
    }
    
    /**
     * Returns the windows framebuffer width. If the window was destroyed an IllegalStateException
     * will be thrown.
     * 
     * @return The windows framebuffer width
     */
    public int getFrameWidth() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        IntBuffer buffer;
        
        try(MemoryStack stack = MemoryStack.stackPush() ) {
            buffer = stack.mallocInt(1);
            GLFW.glfwGetFramebufferSize(windowHandle, buffer, null);
        }
        
        return buffer.get(0);
    }
    
    /**
     * Returns the windows framebuffer height. If the window was destroyed an IllegalStateException
     * will be thrown.
     * 
     * @return The windows framebuffer height
     */
    public int getFrameHeight() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        IntBuffer buffer;
        
        try(MemoryStack stack = MemoryStack.stackPush() ) {
            buffer = stack.mallocInt(1);
            GLFW.glfwGetFramebufferSize(windowHandle, null, buffer);
        }
        
        return buffer.get(0);
    }
    
    /**
     * Will make the windows OpenGL context current (Only necessary if you have multiple windows).
     * If the window was destroyed an IllegalStateException will be thrown.
     */
    public void makeContextCurrent() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        GLFW.glfwMakeContextCurrent(windowHandle);
    }
    
    /**
     * Updates the window, meaning the frame buffers will be swapped and new events are polled.
     * If the window was destroyed an IllegalStateException will be thrown.
     */
    public void update() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        GLFW.glfwSwapBuffers(windowHandle);
        GLFW.glfwPollEvents();
    }
    
    /**
     * Destroys the window and frees its callbacks. If the window was destroyed an
     * IllegalStateException will be thrown.
     */
    public void destroy() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        Callbacks.glfwFreeCallbacks(windowHandle);
        GLFW.glfwDestroyWindow(windowHandle);
        
        destroyed = true;
        windows.remove(this);
    }
    
    /**
     * Returns if the user requested the window to be closed. If the window was destroyed an 
     * IllegalStateException will be thrown.
     * 
     * @return If the user requested a window close.
     */
    public boolean isCloseRequested() {
        if(destroyed)
            throw new IllegalStateException("Window was already destroyed");
        
        return GLFW.glfwWindowShouldClose(windowHandle);
    }
}
