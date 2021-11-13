package com.teacore.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * This class manages an indexed OpenGL VertexArrayObject and provides utility functions to work
 * with it. To help avoid memory leaks, this class also extends OGLAllocatedData.
 * 
 * @author ShietStone
 */
public final class OGLVertexArrayObject extends OGLAllocatedData {

    private int vaoHandle;
    private int[] vboHandles;
    private int vertexCount;
    
    /**
     * This constructor creates an OGLVertexArrayObject with the given vertex arrays and indices.
     * Not that if an argument is null,index is invalid or the vertex arrays do not match an 
     * IllegalArgumentException is thrown. The constructor also automatically registers this object
     * as OGLAllocatedData.
     * 
     * @param vertexArrays The vertex arrays to be contained in this vertex array object.
     * @param indices The indices determining the draw order.
     */
    public OGLVertexArrayObject(OGLVertexArray[] vertexArrays, int[] indices) {
        super();
        
        if(vertexArrays == null || indices == null || containsNull(vertexArrays))
            throw new IllegalArgumentException("An argument is null");
        
        if(!onlyPositiveValues(indices))
            throw new IllegalArgumentException("An index is negative");
        
        if(getMaxValue(indices) > getMinVertexArraySize(vertexArrays))
            throw new IllegalArgumentException("An index is out of bounds of the vertex arrays");
        
        vaoHandle = GL30.glGenVertexArrays();
        vboHandles = new int[vertexArrays.length + 1];
        vertexCount = indices.length;
        
        bind();
        
        for(int index = 0; index < vertexArrays.length; index++)
            makeVBO(index, vertexArrays[index]);
        
        makeIndices(indices);
        
        unbind();
       
        OGLAllocatedData.register(this);
    }
    
    /**
     * Binds this OGLVertexArrayObject to be used in the rendering pipeline. If this object was
     * already deleted an IllegalStateException is thrown.
     */
    public void bind() {
        if(isDeleted())
            throw new IllegalStateException("Vertex array object was already deleted");
        
        GL30.glBindVertexArray(vaoHandle);
    }
    
    /**
     * Unbinds this OGLVertexArrayObject from the rendering pipeline. Note this will unbind any
     * currently bound vertex array object. If this object was already deleted an
     * IllegalStateException is thrown.
     */
    public void unbind() {
        if(isDeleted())
            throw new IllegalStateException("Vertex array object was already deleted");
        
        GL30.glBindVertexArray(0);
    }
    
    /**
     * Enables all buffers (vertex arrays) of this OGLVertexArrayObject. Must be called after
     * bind(). Throws an IllegalStateException if already deleted.
     */
    public void enable() {
        if(isDeleted())
            throw new IllegalStateException("Vertex array object was already deleted");
        
        for(int index = 0; index < vboHandles.length - 1; index++)
            GL20.glEnableVertexAttribArray(index);
    }
    
    /**
     * Disables all buffers (vertex arrays) of this OGLVertexArrayObject. Must be called before
     * unbind(). Throws an IllegalStateException if already deleted.
     */
    public void disable() {
        if(isDeleted())
            throw new IllegalStateException("Vertex array object was already deleted");
        
        for(int index = 0; index < vboHandles.length - 1; index++)
            GL20.glDisableVertexAttribArray(index);
    }
    
    /**
     * This method will initiate a draw call using the currently bound and used components of the
     * rendering pipeline. Will throw an IllegalStateException if this object was deleted already.
     */
    public void draw() {
        if(isDeleted())
            throw new IllegalStateException("Vertex array object was already deleted");
        
        GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
    }
    
    /**
     * Will delete this OGLVertexArrayObject and unregister the OGLAllocatedData. Throws an 
     * IllegalStateException if already deleted.
     */
    @Override
    public void delete() {
        if(isDeleted())
            throw new IllegalStateException("Vertex array object was already deleted");
        
        super.delete();
        OGLAllocatedData.unregister(this);

        GL30.glDeleteVertexArrays(vaoHandle);
        
        for(int vboHandle : vboHandles)
            GL15.glDeleteBuffers(vboHandle);
    }
    
    private boolean containsNull(OGLVertexArray[] vertexArrays) {
        for(OGLVertexArray vertexArray : vertexArrays)
            if(vertexArray == null)
                return true;
        
        return false;
    }
    
    private boolean onlyPositiveValues(int[] values) {
        for(int value : values)
            if(value < 0)
                return false;
        
        return true;
    }
    
    private int getMaxValue(int[] values) {
        int max = Integer.MIN_VALUE;
        
        for(int value : values)
            if(max < value)
                max = value;
        
        return max;
    }
    
    private int getMinVertexArraySize(OGLVertexArray[] vertexArrays) {
        int min = Integer.MAX_VALUE;
        
        for(OGLVertexArray vertexArray : vertexArrays)
            if(min > vertexArray.getTotalSize())
                min = vertexArray.getTotalSize();
        
        return min;
    }
    
    private void makeVBO(int index, OGLVertexArray vertexArray) {
        vboHandles[index] = GL15.glGenBuffers();
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboHandles[index]);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexArray.getVertexData(), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, vertexArray.getVertexSize(), GL11.GL_FLOAT, false, 0, 0);
    }
    
    private void makeIndices(int[] indices) {
        vboHandles[vboHandles.length - 1] = GL15.glGenBuffers();
        
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboHandles[vboHandles.length - 1]);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    }
}
