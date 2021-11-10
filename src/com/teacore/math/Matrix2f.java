package com.teacore.math;

/**
 * A representation of a 2x2 matrix with given commonly needed math functions.
 * 
 * @author ShietStone
 */
public class Matrix2f {

    public float f00;
    public float f01;
    public float f10;
    public float f11;
    
    /**
     * Creates this 2x2 matrix from the data array given. The arrays size must match 2x2
     * (total length 4) and it may not be null. These matrices are handled row major.
     * 
     * @param values The values of the matrix fields in row major order
     */
    public Matrix2f(float[] values) {
        if(values == null)
            throw new IllegalArgumentException("The value array is null");
        
        if(values.length != 4)
            throw new IllegalArgumentException("The value array must have a length of 4");
        
        f00 = values[0];
        f01 = values[1];
        f10 = values[2];
        f11 = values[3];
    }
    
    /**
     * Creates this 2x2 matrix from the given arguments, where each argument is one matrix field. 
     * The arguments are named and handled in row major order.
     * 
     * @param f00 Matrix field 0:0
     * @param f01 Matrix field 0:1
     * @param f10 Matrix field 1:0
     * @param f11 Matrix field 1:1
     */
    public Matrix2f(float f00, float f01,
                    float f10, float f11) {
        this.f00 = f00;
        this.f01 = f01;
        this.f10 = f10;
        this.f11 = f11;
    }
    
    /**
     * Returns a representation of this matrix in as a row major array.
     * 
     * @return This matrix as array
     */
    public float[] asArray() {
        return new float[] {
                f00, f01,
                f10, f11
        };
    }
    
    /**
     * Multiplies a vector with this matrix. The vector may not be null.
     * 
     * @param vector The vector to multiply
     * @return The result of this multiplication
     */
    public Vector2f mul(Vector2f vector) {
        if(vector == null)
            throw new IllegalArgumentException("The vector is null");
        
        return new Vector2f(
                vector.x * f00 + vector.y * f01,
                vector.x * f10 + vector.y * f11);
    }
    
    /**
     * Multiplies a matrix with this matrix. The matrix may not be null.
     * 
     * @param matrix The matrix to multiply
     * @return The result of this multiplication
     */
    public Matrix2f mul(Matrix2f matrix) {
        return new Matrix2f(f00 * matrix.f00 + f01 * matrix.f10,
                            f00 * matrix.f01 + f01 * matrix.f11,
                            f10 * matrix.f00 + f11 * matrix.f10,
                            f10 * matrix.f01 + f11 * matrix.f11);
    }
}
