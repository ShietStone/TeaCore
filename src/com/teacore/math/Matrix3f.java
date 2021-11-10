package com.teacore.math;

/**
 * A representation of a 3x3 matrix with given commonly needed math functions.
 * 
 * @author ShietStone
 */
public class Matrix3f {

    public float f00;
    public float f01;
    public float f02;
    public float f10;
    public float f11;
    public float f12;
    public float f20;
    public float f21;
    public float f22;
    
    /**
     * Creates this 3x3 matrix from the data array given. The arrays size must match 3x3
     * (total length 3) and it may not be null. These matrices are handled row major.
     * 
     * @param values The values of the matrix fields in row major order
     */
    public Matrix3f(float[] values) {
        if(values == null)
            throw new IllegalArgumentException("The value array is null");
        
        if(values.length != 9)
            throw new IllegalArgumentException("The value array must have a length of 9");
        
        f00 = values[0];
        f01 = values[1];
        f02 = values[2];
        f10 = values[3];
        f11 = values[4];
        f12 = values[5];
        f20 = values[6];
        f21 = values[7];
        f22 = values[8];
    }
    
    /**
     * Creates this 3x3 matrix from the given arguments, where each argument is one matrix field. 
     * The arguments are named and handled in row major order.
     * 
     * @param f00 Matrix field 0:0
     * @param f01 Matrix field 0:1
     * @param f02 Matrix field 0:2
     * @param f10 Matrix field 1:0
     * @param f11 Matrix field 1:1
     * @param f12 Matrix field 1:2
     * @param f20 Matrix field 2:0
     * @param f21 Matrix field 2:1
     * @param f22 Matrix field 2:2
     */
    public Matrix3f(float f00, float f01, float f02,
                    float f10, float f11, float f12,
                    float f20, float f21, float f22) {
        this.f00 = f00;
        this.f01 = f01;
        this.f02 = f02;
        this.f10 = f10;
        this.f11 = f11;
        this.f12 = f12;
        this.f20 = f20;
        this.f21 = f21;
        this.f22 = f22;
    }
    
    /**
     * Returns a representation of this matrix in as a row major array.
     * 
     * @return This matrix as array
     */
    public float[] asArray() {
        return new float[] {
                f00, f01, f02,
                f10, f11, f12,
                f20, f21, f22
        };
    }
    
    /**
     * Multiplies a vector with this matrix. The vector may not be null.
     * 
     * @param vector The vector to multiply
     * @return The result of this multiplication
     */
    public Vector3f mul(Vector3f vector) {
        if(vector == null)
            throw new IllegalArgumentException("The vector is null");
        
        return new Vector3f(
                vector.x * f00 + vector.y * f01 + vector.z * f02,
                vector.x * f10 + vector.y * f11 + vector.z * f12,
                vector.x * f20 + vector.y * f21 + vector.z * f22);
    }
    
    /**
     * Multiplies a matrix with this matrix. The matrix may not be null.
     * 
     * @param matrix The matrix to multiply
     * @return The result of this multiplication
     */
    public Matrix3f mul(Matrix3f matrix) {
        return new Matrix3f(f00 * matrix.f00 + f01 * matrix.f10 + f02 * matrix.f20,
                            f00 * matrix.f01 + f01 * matrix.f11 + f02 * matrix.f21,
                            f00 * matrix.f02 + f01 * matrix.f12 + f02 * matrix.f22,
                            f10 * matrix.f00 + f11 * matrix.f10 + f12 * matrix.f20,
                            f10 * matrix.f01 + f11 * matrix.f11 + f12 * matrix.f21,
                            f10 * matrix.f02 + f11 * matrix.f12 + f12 * matrix.f22,
                            f20 * matrix.f00 + f21 * matrix.f10 + f22 * matrix.f20,
                            f20 * matrix.f01 + f21 * matrix.f11 + f22 * matrix.f21,
                            f20 * matrix.f02 + f21 * matrix.f12 + f22 * matrix.f22);
    }
}
