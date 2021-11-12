package com.teacore.math;

/**
 * A representation of a 4x4 matrix with given commonly needed math functions.
 * 
 * @author ShietStone
 */
public class Matrix4f {

    public float f00;
    public float f01;
    public float f02;
    public float f03;
    public float f10;
    public float f11;
    public float f12;
    public float f13;
    public float f20;
    public float f21;
    public float f22;
    public float f23;
    public float f30;
    public float f31;
    public float f32;
    public float f33;
    
    /**
     * Creates this 4x4 matrix from the data array given. The arrays size must match 4x4
     * (total length 16) and it may not be null. These matrices are handled row major.
     * 
     * @param values The values of the matrix fields in row major order
     */
    public Matrix4f(float[] values) {
        if(values == null)
            throw new IllegalArgumentException("The value array is null");
        
        if(values.length != 16)
            throw new IllegalArgumentException("The value array must have a length of 16");
        
        f00 = values[0];
        f01 = values[1];
        f02 = values[2];
        f03 = values[3];
        f10 = values[4];
        f11 = values[5];
        f12 = values[6];
        f13 = values[7];
        f20 = values[8];
        f21 = values[9];
        f22 = values[10];
        f23 = values[11];
        f30 = values[12];
        f31 = values[13];
        f32 = values[14];
        f33 = values[15];
    }
    
    /**
     * Creates this 4x4 matrix from the given arguments, where each argument is one matrix field. 
     * The arguments are named and handled in row major order.
     * 
     * @param f00 Matrix field 0:0
     * @param f01 Matrix field 0:1
     * @param f02 Matrix field 0:2
     * @param f03 Matrix field 0:3
     * @param f10 Matrix field 1:0
     * @param f11 Matrix field 1:1
     * @param f12 Matrix field 1:2
     * @param f13 Matrix field 1:3
     * @param f20 Matrix field 2:0
     * @param f21 Matrix field 2:1
     * @param f22 Matrix field 2:2
     * @param f23 Matrix field 2:3
     * @param f30 Matrix field 3:0
     * @param f31 Matrix field 3:1
     * @param f32 Matrix field 3:2
     * @param f33 Matrix field 3:3
     */
    public Matrix4f(float f00, float f01, float f02, float f03,
                    float f10, float f11, float f12, float f13,
                    float f20, float f21, float f22, float f23,
                    float f30, float f31, float f32, float f33) {
        this.f00 = f00;
        this.f01 = f01;
        this.f02 = f02;
        this.f03 = f03;
        this.f10 = f10;
        this.f11 = f11;
        this.f12 = f12;
        this.f13 = f13;
        this.f20 = f20;
        this.f21 = f21;
        this.f22 = f22;
        this.f23 = f23;
        this.f30 = f30;
        this.f31 = f31;
        this.f32 = f32;
        this.f33 = f33;
    }
    
    /**
     * Returns a representation of this matrix in as a row major array.
     * 
     * @return This matrix as array
     */
    public float[] asArray() {
        return new float[] {
                f00, f01, f02, f03,
                f10, f11, f12, f13,
                f20, f21, f22, f23,
                f30, f31, f32, f33
        };
    }
    
    /**
     * Multiplies a vector with this matrix. The vector may not be null.
     * 
     * @param vector The vector to multiply
     * @return The result of this multiplication
     */
    public Vector4f mul(Vector4f vector) {
        if(vector == null)
            throw new IllegalArgumentException("The vector is null");
        
        return new Vector4f(
                vector.x * f00 + vector.y * f01 + vector.z * f02 + vector.w * f03,
                vector.x * f10 + vector.y * f11 + vector.z * f12 + vector.w * f13,
                vector.x * f20 + vector.y * f21 + vector.z * f22 + vector.w * f23,
                vector.x * f30 + vector.y * f31 + vector.z * f32 + vector.w * f33);
    }
    
    //TODO Duplicate of matrix.f02
    /**
     * Multiplies a matrix with this matrix. The matrix may not be null.
     * 
     * @param matrix The matrix to multiply
     * @return The result of this multiplication
     */
    public Matrix4f mul(Matrix4f matrix) {
        return new Matrix4f(f00 * matrix.f00 + f01 * matrix.f10 + f02 * matrix.f20 + f03 * matrix.f30,
                            f00 * matrix.f01 + f01 * matrix.f11 + f02 * matrix.f21 + f03 * matrix.f31,
                            f00 * matrix.f02 + f01 * matrix.f12 + f02 * matrix.f22 + f03 * matrix.f32,
                            f00 * matrix.f03 + f01 * matrix.f13 + f02 * matrix.f23 + f03 * matrix.f33,
                            f10 * matrix.f00 + f11 * matrix.f10 + f12 * matrix.f20 + f13 * matrix.f30,
                            f10 * matrix.f01 + f11 * matrix.f11 + f12 * matrix.f21 + f13 * matrix.f31,
                            f10 * matrix.f02 + f11 * matrix.f12 + f12 * matrix.f22 + f13 * matrix.f32,
                            f10 * matrix.f03 + f11 * matrix.f13 + f12 * matrix.f23 + f13 * matrix.f33,
                            f20 * matrix.f00 + f21 * matrix.f10 + f22 * matrix.f20 + f23 * matrix.f30,
                            f20 * matrix.f01 + f21 * matrix.f11 + f22 * matrix.f21 + f23 * matrix.f31,
                            f20 * matrix.f02 + f21 * matrix.f12 + f22 * matrix.f22 + f23 * matrix.f32,
                            f20 * matrix.f03 + f21 * matrix.f13 + f22 * matrix.f23 + f23 * matrix.f33,
                            f30 * matrix.f00 + f31 * matrix.f10 + f32 * matrix.f20 + f33 * matrix.f30,
                            f30 * matrix.f01 + f31 * matrix.f11 + f32 * matrix.f21 + f33 * matrix.f31,
                            f30 * matrix.f02 + f31 * matrix.f12 + f32 * matrix.f22 + f33 * matrix.f32,
                            f30 * matrix.f03 + f31 * matrix.f13 + f32 * matrix.f23 + f33 * matrix.f33);
    }
}
