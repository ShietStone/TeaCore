package com.teacore.math;

/**
 * Represents a 3-dimensional vector and provides utility math functions.
 * 
 * @author ShietStone
 */
public class Vector4f {

    public float x;
    public float y;
    public float z;
    public float w;
    
    /**
     * Stores the given coordinate values.
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param w The w coordinate
     */
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    /**
     * Adds a vectors values to this vectors values. This object itself is not altered.
     * 
     * @param vector The vector to add the values from
     * @return The result
     */
    public Vector4f add(Vector4f vector) {
        return new Vector4f(x + vector.x, y + vector.y, z + vector.z, w + vector.w);
    }
    
    /**
     * Subtracts a vectors values from this vectors values. This object itself is not altered.
     * 
     * @param vector The vector to subtract with
     * @return The result
     */
    public Vector4f sub(Vector4f vector) {
        return new Vector4f(x - vector.x, y - vector.y, z - vector.z, w - vector.w);    
    }

    /**
     * Multiplies a vectors values with this vectors values. This object itself is not altered.
     * 
     * @param vector The vector to multiply the values with
     * @return The result
     */
    public Vector4f mul(Vector4f vector) {
        return new Vector4f(x * vector.x, y * vector.y, z * vector.z, w * vector.w);
    }
    
    /**
     * Divides this vectors values by another vectors values. This object itself is not altered.
     * 
     * @param vector The vector to divide the values by
     * @return The result
     */
    public Vector4f div(Vector4f vector) {
        return new Vector4f(x / vector.x, y / vector.y, z / vector.z, w / vector.w);    
    }
    
    /**
     * Adds a single value to this vectors value. This object itself is not altered.
     * 
     * @param value The value to add
     * @return The result
     */
    public Vector4f add(float value) {
        return new Vector4f(x + value, y + value, z + value, w + value);
    }
    
    /**
     * Subtracts a single value to this vectors value. This object itself is not altered.
     * 
     * @param value The value to subtract
     * @return The result
     */
    public Vector4f sub(float value) {
        return new Vector4f(x - value, y - value, z - value, w - value);
    }
    
    /**
     * Multiplies a single value with this vectors value. This object itself is not altered.
     * 
     * @param value The value to multiply with
     * @return The result
     */
    public Vector4f mul(float value) {
        return new Vector4f(x * value, y * value, z * value, w * value);
    }
    
    /**
     * Divides this vectors values by a single value. This object itself is not altered.
     * 
     * @param value The value to divide by
     * @return The result
     */
    public Vector4f div(float value) {
        return new Vector4f(x / value, y / value, z / value, w / value);
    }
    
    /**
     * Calculates the vectors length.
     * 
     * @return The vectors length
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }
    
    /**
     * Normalizes this vector. This means, it is divided by its length, this way making its new 
     * length ~1.0. This object itself is not altered.
     * 
     * @return The result
     */
    public Vector4f normalize() {
        float length = length();
        
        if(length == 0.0f)
            length = 1.0f;
        
        return new Vector4f(x / length, y / length, z / length, w / length);
    }
    
    /**
     * Returns the dot product of this vector and a given one.
     * 
     * @param vector The vector to calculate the dot product with
     * @return The dot product
     */
    public float dot(Vector4f vector) {
        return x * vector.x + y * vector.y + z * vector.z + w * vector.w;
    }
}
