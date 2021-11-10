package com.teacore.math;

/**
 * Represents a 3-dimensional vector and provides utility math functions.
 * 
 * @author ShietStone
 */
public class Vector3f {

    public float x;
    public float y;
    public float z;
    
    /**
     * Stores the given coordinate values.
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Adds a vectors values to this vectors values. This object itself is not altered.
     * 
     * @param vector The vector to add the values from
     * @return The result
     */
    public Vector3f add(Vector3f vector) {
        return new Vector3f(x + vector.x, y + vector.y, z + vector.z);
    }
    
    /**
     * Subtracts a vectors values from this vectors values. This object itself is not altered.
     * 
     * @param vector The vector to subtract with
     * @return The result
     */
    public Vector3f sub(Vector3f vector) {
        return new Vector3f(x - vector.x, y - vector.y, z - vector.z);    
    }

    /**
     * Multiplies a vectors values with this vectors values. This object itself is not altered.
     * 
     * @param vector The vector to multiply the values with
     * @return The result
     */
    public Vector3f mul(Vector3f vector) {
        return new Vector3f(x * vector.x, y * vector.y, z * vector.z);
    }
    
    /**
     * Divides this vectors values by another vectors values. This object itself is not altered.
     * 
     * @param vector The vector to divide the values by
     * @return The result
     */
    public Vector3f div(Vector3f vector) {
        return new Vector3f(x / vector.x, y / vector.y, z / vector.z);    
    }
    
    /**
     * Adds a single value to this vectors value. This object itself is not altered.
     * 
     * @param value The value to add
     * @return The result
     */
    public Vector3f add(float value) {
        return new Vector3f(x + value, y + value, z + value);
    }
    
    /**
     * Subtracts a single value to this vectors value. This object itself is not altered.
     * 
     * @param value The value to subtract
     * @return The result
     */
    public Vector3f sub(float value) {
        return new Vector3f(x - value, y - value, z - value);
    }
    
    /**
     * Multiplies a single value with this vectors value. This object itself is not altered.
     * 
     * @param value The value to multiply with
     * @return The result
     */
    public Vector3f mul(float value) {
        return new Vector3f(x * value, y * value, z * value);
    }
    
    /**
     * Divides this vectors values by a single value. This object itself is not altered.
     * 
     * @param value The value to divide by
     * @return The result
     */
    public Vector3f div(float value) {
        return new Vector3f(x / value, y / value, z / value);
    }
    
    /**
     * Calculates the vectors length.
     * 
     * @return The vectors length
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
    
    /**
     * Normalizes this vector. This means, it is divided by its length, this way making its new 
     * length ~1.0. This object itself is not altered.
     * 
     * @return The result
     */
    public Vector3f normalize() {
        float length = length();
        
        if(length == 0.0f)
            length = 1.0f;
        
        return new Vector3f(x / length, y / length, z / length);
    }
    
    /**
     * Returns the dot product of this vector and a given one.
     * 
     * @param vector The vector to calculate the dot product with
     * @return The dot product
     */
    public float dot(Vector3f vector) {
        return x * vector.x + y * vector.y + z * vector.z;
    }
    
    /**
     * Returns the cross product of this vector and another.
     * 
     * @param vector The vector to calculate the cross product with
     * @return The result of the cross product
     */
    public Vector3f cross(Vector3f vector) {
        return new Vector3f(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x);
    }
}
