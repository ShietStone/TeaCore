package com.teacore.math;

/**
 * This class provides some utility math functions, mostly the creation of common matrices.
 * 
 * @author ShietStone
 */
public class MathUtil {
    
    /**
     * Returns a 4x4 projection matrix defined by the given parameters.
     * 
     * @param aspect The aspect ratio (width divided by height) of the target screen
     * @param near The near clipping plane (z value)
     * @param far The far clipping plane (z value)
     * @param fov The field of view (angle value)
     * @return The projection matrix to map 3D points onto a 2D plane
     */
    public static Matrix4f getProjectionMatrix(float aspect, float near, float far, float fov) {
        float h = (float) Math.tan(Math.toRadians(fov / 2f));
        float a = - (near + far) / (near - far);
        float b = - ((2 * far * near) / (far - near));
    
        float[] content = new float[] {
                h, 0, 0, 0,
                0, h * aspect, 0, 0,
                0, 0, a, 1,
                0, 0, b, 0
        };
    
        return new Matrix4f(content);
    }
    
    /**
     * Returns the inverse of an projection matrix. This can be used for mouse picking in a 3D
     * scene. This does not generally invert any given matrix, so if given a non-projection matrix
     * the mathematical result is unknown. IllegalArgumentException will be thrown if the matrix
     * is null.
     * 
     * @param projectionMatrix The projection matrix to invert
     * @return The inverted projection matrix
     */
    public static Matrix4f getInverseProjectionMatrix(Matrix4f projectionMatrix) {
        if(projectionMatrix == null)
            throw new IllegalArgumentException("Given matrix is null");
        
        float a = projectionMatrix.f00;
        float b = projectionMatrix.f11;
        float c = projectionMatrix.f22;
        float d = projectionMatrix.f23;
        float e = projectionMatrix.f32;
        
        return new Matrix4f(new float[] {
                1.0f / a, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f / b, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f / e,
                0.0f, 0.0f, 1.0f / d, -c / (d * e)
        });
    }
    
    /**
     * Returns the rotation matrix around the x axis by a given angle.
     * 
     * @param angle The angle to rotate by
     * @return The rotation matrix
     */
    public static Matrix3f getRotationMatrixX(float angle) {
        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        
        return new Matrix3f(new float[] {
                1.0f, 0.0f, 0.0f,
                0.0f, cos, -sin,
                0.0f, sin, cos
        });
    }
    
    /**
     * Returns the rotation matrix around the y axis by a given angle.
     * 
     * @param angle The angle to rotate by
     * @return The rotation matrix
     */
    public static Matrix3f getRotationMatrixY(float angle) {
        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        
        return new Matrix3f(new float[] {
                cos, 0.0f, sin,
                0.0f, 1.0f, 0.0f,
                -sin, 0.0f, cos
        });
    }
    
    /**
     * Returns the rotation matrix around the z axis by a given angle.
     * 
     * @param angle The angle to rotate by
     * @return The rotation matrix
     */
    public static Matrix3f getRotationMatrixZ(float angle) {
        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        
        return new Matrix3f(new float[] {
                cos, -sin, 0.0f,
                sin, cos, 0.0f,
                0.0f, 0.0f, 1.0f
        });
    }
    
    /**
     * Returns a rotation matrix for 2D space. This matrix would be the equivalent to rotating 
     * around the z axis in 3D.
     * 
     * @param angle The angle to rotate by
     * @return The rotation matrix
     */
    public static Matrix2f getRotationMatrix(float angle) {
        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        
        return new Matrix2f(new float[] {
                cos, -sin,
                sin, cos
        });
    }
}
