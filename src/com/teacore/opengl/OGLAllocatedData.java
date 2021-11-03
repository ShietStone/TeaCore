package com.teacore.opengl;

import java.util.ArrayList;

/**
 * This class manages allocated data (often on the GPU) by OpenGL. This way using deleteAll()
 * every not yet deleted data will be deleted, thus preventing memory leaks.
 * 
 * @author ShietStone
 */
public class OGLAllocatedData {

    private static final ArrayList<OGLAllocatedData> instanceList;
    
    static {
        instanceList = new ArrayList<>();
    }
    
    /**
     * Registers an OGLAllocatedData object. If null is passed nothing happens.
     * 
     * @param data The allocated data to register
     */
    public static void register(OGLAllocatedData data) {
        if(data != null)
            instanceList.add(data);
    }
    
    //TODO Documentation links e.g. {@link OGLAllocatedData}
    
    /**
     * Unregisters an OGLAllocatedData object. The delete() method is not called.
     * 
     * @param data The allocated data to unregister
     */
    public static void unregister(OGLAllocatedData data) {
        instanceList.remove(data);
    }
    
    /**
     * Deletes all registered OGLAllocatedData instances that were not deleted yet. 
     */
    public static void deleteAll() {
        while(instanceList.size() > 0) {
            OGLAllocatedData allocatedData = instanceList.get(0);
            
            if(!allocatedData.isDeleted())
                allocatedData.delete();
            
            instanceList.remove(0);
        }
    }
    
    //TODO Create a Deletable interface and make a manager class, deleted flag should be managed by the inheriting classes
    
    private boolean deleted;
    
    /**
     * Creates an empty OGLAllocatedData object and sets the deleted flag to false.
     */
    public OGLAllocatedData() {
        deleted = false;
    }
    
    /**
     * Returns if this OGLAllocatedData was already deleted or not.
     * 
     * @return If this allocated data was deleted
     */
    public boolean isDeleted() {
        return deleted;
    }
    
    /**
     * Deletes this allocated data.
     */
    public void delete() {
        deleted = true;
    }
}
