package com.teacore.opengl;

import org.lwjgl.opengl.GL20;

import com.teacore.glfw.GLFWWindow;

/**
 * This class manages an OpenGL shader and further provides methods that wrap the corresponding GL
 * calls. To avoid memory leaks this class extends OGLAllocatedData and (un-)registers itself there
 * automatically.
 * 
 * @author ShietStone
 */
public final class OGLShader extends OGLAllocatedData {

    private int vertexShaderHandle;
    private int fragmentShaderHandle;
    private int programHandle;
    
    /**
     * Creates, compiles and links an OpenGL shader program using the provided vertex and fragment
     * shader code. Both arguments may not be null. Should either the compiling or linking fail an
     * OGLShaderCompileException with the corresponding logging info will be thrown.
     * 
     * @param vertexShaderCode The source code of the vertex shader
     * @param fragmentShaderCode The source code of the fragment shader
     * @throws OGLShaderCompileException If the compilation or linking fails
     */
    public OGLShader(String vertexShaderCode, String fragmentShaderCode) throws OGLShaderCompileException {
        super();
        
        if(vertexShaderCode == null || fragmentShaderCode == null)
            throw new IllegalArgumentException("An argument is null");
        
        vertexShaderHandle = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShaderHandle, vertexShaderCode);
        GL20.glCompileShader(vertexShaderHandle);
        
        if(GL20.glGetShaderi(vertexShaderHandle, GL20.GL_COMPILE_STATUS) == 0) {
            String vertexShaderLog = GL20.glGetShaderInfoLog(vertexShaderHandle);
            GL20.glDeleteShader(vertexShaderHandle);
            throw new OGLShaderCompileException("Vertex shader could not compile", vertexShaderLog);
        }
        
        fragmentShaderHandle = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShaderHandle, fragmentShaderCode);
        GL20.glCompileShader(fragmentShaderHandle);
        
        if(GL20.glGetShaderi(fragmentShaderHandle, GL20.GL_COMPILE_STATUS) == 0) {
            String fragmentShaderLog = GL20.glGetShaderInfoLog(fragmentShaderHandle);
            GL20.glDeleteShader(vertexShaderHandle);
            GL20.glDeleteShader(fragmentShaderHandle);
            throw new OGLShaderCompileException("Fragment shader could not compile", fragmentShaderLog);
        }
        
        programHandle = GL20.glCreateProgram();
        GL20.glAttachShader(programHandle, vertexShaderHandle);
        GL20.glAttachShader(programHandle, fragmentShaderHandle);
        GL20.glLinkProgram(programHandle);
        
        if(GL20.glGetProgrami(programHandle, GL20.GL_LINK_STATUS) == 0) {
            String programLog = GL20.glGetProgramInfoLog(programHandle);
            GL20.glDeleteShader(vertexShaderHandle);
            GL20.glDeleteShader(fragmentShaderHandle);
            GL20.glDeleteProgram(programHandle);
            throw new OGLShaderCompileException("Shader program could not link", programLog);    
        }

        OGLAllocatedData.register(this);
    }
    
    /**
     * Sets this shader program to be used in the rendering pipeline. Throws an 
     * IllegalStateException if the program was already deleted or the wrong OpenGL context is
     * current.
     */
    public void use() {
        if(isDeleted())
            throw new IllegalStateException("The shader program was already deleted");
        
        if(getContext() != GLFWWindow.getCurrentContext())
            throw new IllegalStateException("The wrong OpenGL context is current");
        
        GL20.glUseProgram(programHandle);
    }
    
    /**
     * Removes this shader program from active use. Note that this call would also remove any other
     * shader program from active use, not just this one. If this program was already deleted or the 
     * wrong OpenGL context is current an IllegalStateException is thrown.
     */
    public void stopUse() {
        if(isDeleted())
            throw new IllegalStateException("The shader program was already deleted");
        
        if(getContext() != GLFWWindow.getCurrentContext())
            throw new IllegalStateException("The wrong OpenGL context is current");
        
        GL20.glUseProgram(0);
    }
    
    /**
     * Returns the location of any uniform field in this shader program, specified by the given
     * name. If the name is null an IllegalArgumentException is thrown. If the shader program was
     * already deleted or the wrong OpenGL context is current an IllegalStateException is thrown.
     * 
     * @param name The uniform fields name
     * @return The location to upload the data to
     */
    public int getUniformLocation(String name) {
        if(name == null)
            throw new IllegalArgumentException("An argument is null");
            
        if(isDeleted())
            throw new IllegalStateException("The shader program was already deleted");

        if(getContext() != GLFWWindow.getCurrentContext())
            throw new IllegalStateException("The wrong OpenGL context is current");
        
        return GL20.glGetUniformLocation(programHandle, name);
    }
    
    /**
     * Deletes this shader program and unregisters this object from OGLAllocatedData. An
     * IllegalStateException is thrown if it was already deleted or the wrong OpenGL context is 
     * current.
     */
    @Override
    public void delete() {
        if(isDeleted())
            throw new IllegalStateException("The shader program was already deleted");
        
        if(getContext() != GLFWWindow.getCurrentContext())
            throw new IllegalStateException("The wrong OpenGL context is current");
        
        super.delete();
        OGLAllocatedData.unregister(this);
        
        GL20.glDetachShader(programHandle, vertexShaderHandle);
        GL20.glDetachShader(programHandle, fragmentShaderHandle);
        GL20.glDeleteShader(vertexShaderHandle);
        GL20.glDeleteShader(fragmentShaderHandle);
        GL20.glDeleteProgram(programHandle);        
    }
}
