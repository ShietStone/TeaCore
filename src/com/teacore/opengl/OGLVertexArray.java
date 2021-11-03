package com.teacore.opengl;

/**
 * Represents a single vertex array that can be loaded into an OGLVertexArrayObject.
 * Only contains data.
 * 
 * @author ShietStone
 */
public final class OGLVertexArray {

    private float[] vertexData;
    private int vertexSize;
    
    /**
     * Saves the given data. If the arguments are null or invalid an IllegalArgumentException is
     * thrown.
     * 
     * @param vertexData The raw data packed as array
     * @param vertexSize The data size (in elements) per vertex
     */
    public OGLVertexArray(float[] vertexData, int vertexSize) {
        if(vertexData == null)
            throw new IllegalArgumentException("Vertex data is null");
        
        if(vertexSize < 1)
            throw new IllegalArgumentException("Vertex size is less than one");
        
        if(vertexData.length % vertexSize != 0)
            throw new IllegalArgumentException("Vertex data and vertex size do not match");
        
        this.vertexData = vertexData;
        this.vertexSize = vertexSize;
    }
    
    /**
     * Returns the raw data packed as array. Its the same object previously given in the
     * constructor,
     * 
     * @return The raw data
     */
    public float[] getVertexData() {
        return vertexData;
    }
    
    /**
     * Returns the data size (in elements) per vertex.
     * 
     * @return The vertex size
     */
    public int getVertexSize() {
        return vertexSize;
    }
    
    /**
     * Returns how many vertices are contained in the data array.
     * 
     * @return The vertex count
     */
    public int getTotalSize() {
        return vertexData.length / vertexSize;
    }
}
