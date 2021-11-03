package com.teacore.glfw;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;

/**
 * A utility layer to so GLFW monitors can be handled as objects. This class needs some rework, as
 * if a monitor is (dis-)connected some objects may not be valid anymore. For this the invalidAll()
 * method should be called, but the event handler for that is not implemented yet. In the future
 * only disconnected monitors should be invalidated.
 * 
 * @author ShietStone
 */
public final class GLFWMonitor {

    private static GLFWMonitor[] monitors;
    
    private long handle;
    private boolean valid;
    
    private GLFWMonitor(long handle) {
        this.handle = handle;
        valid = true;
    }
    
    /**
     * Returns all currently connected and detected monitors. Note, that if one disconnects the
     * object becomes invalid, which currently cannot be tested via isValid(). The result is
     * buffered until invalidateAll() is called, in which case this method will fetch the connected
     * monitors again.
     * 
     * @return An array of all monitors
     */
    public static GLFWMonitor[] getMonitors() {
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
     * should be none.
     * 
     * @return Returns the primary monitor
     */
    public static GLFWMonitor getPrimary() {
        if(monitors == null)
            getMonitors();
        
        return monitors.length > 0 ? monitors[0] : null;
    }
    
    /**
     * Invalidates all GLFWMonitor objects created in the last getMonitors() call.
     */
    public static void invalidateAll() {
        if(monitors != null)
            for(GLFWMonitor monitor : monitors)
                monitor.valid = false;
    
        monitors = null;
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
     * Returns if this object is still valid. This method is not reliable yet but will be in the
     * future.
     * 
     * @return If this monitor is still valid
     */
    public boolean isValid() {
        return valid;
    }
}
