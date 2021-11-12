package com.teacore.glfw;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallbackI;

/**
 * A utility layer to so GLFW monitors can be handled as objects. Note, that if the monitor 
 * configuration changes (for example by connecting/disconnecting) all GLFWMonitor objects will be
 * invalidated and need to be fetched again. This behavior is planned to change in the future to 
 * only invalidate the affected monitor handles. 
 * 
 * @author ShietStone
 */
public final class GLFWMonitor {

    private static GLFWMonitor[] monitors;
    
    //TODO Only invalidate affected monitor handles
    
    static {
        if(!GLFWUtil.isInitialized() || GLFWUtil.isTerminated())
            throw new IllegalStateException("GLFW is not ready");
        
        GLFW.glfwSetMonitorCallback(new GLFWMonitorCallbackI() {

            @Override
            public void invoke(long handle, int event) {
                invalidateAll();
            }
        });
    }
    
    /**
     * Returns all currently connected and detected monitors. Note, that if the configuration 
     * changes (e.g. connect/disconnect) all GLFWMonitor objects become invalid (tested via 
     * isValid()) and need to be fetched again. Throws an IllegalStateException if GLFW is not 
     * ready.
     * 
     * @return An array of all detected monitors
     */
    public static GLFWMonitor[] getMonitors() {
        if(!GLFWUtil.isInitialized() || GLFWUtil.isTerminated())
            throw new IllegalStateException("GLFW is not ready");
        
        if(monitors == null) {
            PointerBuffer buffer = GLFW.glfwGetMonitors();
            monitors = new GLFWMonitor[buffer.capacity()];
        
            for(int index = 0; index < monitors.length; index++)
                monitors[index] = new GLFWMonitor(buffer.get(index));
        }
        
        return monitors;
    }
    
    /**
     * Returns the primary monitor of the system. Null is returned in the unlikely case that there
     * should be none. Throws an IllegalStateException if GLFW is not ready.
     * 
     * @return Returns the primary monitor
     */
    public static GLFWMonitor getPrimary() {
        if(!GLFWUtil.isInitialized() || GLFWUtil.isTerminated())
            throw new IllegalStateException("GLFW is not ready");
        
        if(monitors == null)
            getMonitors();
        
        return monitors.length > 0 ? monitors[0] : null;
    }
    
    /**
     * Invalidates all GLFWMonitor objects created in the last getMonitors() call. Throws an
     * IllegalStateException if GLFW is not ready.
     */
    public static void invalidateAll() {
        if(!GLFWUtil.isInitialized() || GLFWUtil.isTerminated())
            throw new IllegalStateException("GLFW is not ready");
        
        if(monitors != null)
            for(GLFWMonitor monitor : monitors)
                monitor.valid = false;
    
        monitors = null;
    }
    
    private long handle;
    private boolean valid;
    
    private GLFWMonitor(long handle) {
        this.handle = handle;
        valid = true;
    }
    
    /**
     * Returns the handled address value of this monitor.
     * 
     * @return The handled address
     */
    public long getHandle() {
        return handle;
    }
    
    /**
     * Returns if this object and its handle are still valid.
     * 
     * @return If this monitor is still valid
     */
    public boolean isValid() {
        return valid;
    }
}
