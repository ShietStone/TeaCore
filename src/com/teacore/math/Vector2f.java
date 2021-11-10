package com.teacore.math;

/**
 * Represents a 2-dimensional vector and provides utility math functions.
 * 
 * @author ShietStone
 */
public class Vector2f {

    public float x;
    public float y;
    
    /**
     * Stores the given coordinate values.
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    //TODO Avoid null vectors as arguments
    
    /**
     * Adds a vectors values to this vectors values. This object itself is not altered.
     * 
     * @param vector The vector to add the values from
     * @return The result
     */
    public Vector2f add(Vector2f vector) {
        return new Vector2f(x + vector.x, y + vector.y);
    }
    
    /**
     * Subtracts a vectors values from this vectors values. This object itself is not altered.
     * 
     * @param vector The vector to subtract with
     * @return The result
     */
    public Vector2f sub(Vector2f vector) {
        return new Vector2f(x - vector.x, y - vector.y);    
    }

    /**
     * Multiplies a vectors values with this vectors values. This object itself is not altered.
     * 
     * @param vector The vector to multiply the values with
     * @return The result
     */
    public Vector2f mul(Vector2f vector) {
        return new Vector2f(x * vector.x, y * vector.y);    
    }
    
    /**
     * Divides this vectors values by another vectors values. This object itself is not altered.
     * 
     * @param vector The vector to divide the values by
     * @return The result
     */
    public Vector2f div(Vector2f vector) {
        return new Vector2f(x / vector.x, y / vector.y);    
    }
    
    /**
     * Adds a single value to this vectors value. This object itself is not altered.
     * 
     * @param value The value to add
     * @return The result
     */
    public Vector2f add(float value) {
        return new Vector2f(x + value, y + value);
    }
    
    /**
     * Subtracts a single value to this vectors value. This object itself is not altered.
     * 
     * @param value The value to subtract
     * @return The result
     */
    public Vector2f sub(float value) {
        return new Vector2f(x - value, y - value);
    }
    
    /**
     * Multiplies a single value with this vectors value. This object itself is not altered.
     * 
     * @param value The value to multiply with
     * @return The result
     */
    public Vector2f mul(float value) {
        return new Vector2f(x * value, y * value);
    }
    
    /**
     * Divides this vectors values by a single value. This object itself is not altered.
     * 
     * @param value The value to divide by
     * @return The result
     */
    public Vector2f div(float value) {
        return new Vector2f(x / value, y / value);
    }
    
    /**
     * Calculates the vectors length.
     * 
     * @return The vectors length
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }
    
    /**
     * Normalizes this vector. This means, it is divided by its length, this way making its new 
     * length ~1.0. This object itself is not altered.
     * 
     * @return The result
     */
    public Vector2f normalize() {
        float length = length();
        
        if(length == 0.0f)
            length = 1.0f;
        
        return new Vector2f(x / length, y / length);
    }
    
    /**
     * Returns the dot product of this vector and a given one.
     * 
     * @param vector The vector to calculate the dot product with
     * @return The dot product
     */
    public float dot(Vector2f vector) {
        return x * vector.x + y * vector.y;
    }
}
