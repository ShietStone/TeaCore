package com.teacore.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

/**
 * A utility layer for general GLFW calls, like initializing and terminating.
 * 
 * @author ShietStone
 */
public final class GLFWUtil {

    private static boolean initialized;
    private static boolean terminated;
    
    static {
        initialized = false;
        terminated = false;
    }
    
    /**
     * Initialized GLFW and sets the error callback. If it was already terminated, initialized or
     * the call failed then an IllegalStateException will be thrown.
     */
    public static void init() {
        if(terminated)
            throw new IllegalStateException("GLFW was already terminated");
        
        if(initialized)
            throw new IllegalStateException("GLFW already got initialized");
        
        GLFWErrorCallback.createPrint(System.err).set();
        
        if(!GLFW.glfwInit())
            throw new IllegalStateException("GLFW not initialized");
        
        initialized = true;
    }
    
    /**
     * Terminates GLFW,frees the error callback and destroys all GLFWWindows. If it was not
     * initialized or already terminated then an IllegalStateException will be thrown. 
     */
    public static void terminate() {
        if(!initialized)
            throw new IllegalStateException("GLFW did not get initialized");
        
        if(terminated)
            throw new IllegalStateException("GLFW already got terminated");
        
        GLFWWindow.destroyAll();
        
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
        
        terminated = true;
    }
    
    /**
     * Returns if GLFW was initialized.
     * 
     * @return If GLFW was initialized.
     */
    public static boolean isInitialized() {
        return initialized;
    }
    
    /**
     * Returns if GLFW was terminated.
     * 
     * @return If GLFW was terminated.
     */
    public static boolean isTerminated() {
        return terminated;
    }
}
