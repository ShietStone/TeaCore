package com.teacore.opengl;

/**
 * An Exception that is intended to be thrown when errors in shader compilation and program 
 * linking occur.
 * 
 * @author ShietStone
 */
public final class OGLShaderCompileException extends Exception {

    private static final long serialVersionUID = 1L;

    private String log;
    
    /**
     * Creates an exception with the given message and info log.
     * 
     * @param message The error message
     * @param log The corresponding compile/link info log
     */
    public OGLShaderCompileException(String message, String log) {
        super(message);
        this.log = log;
    }
    
    /**
     * Returns the exception specific info log, for example of the compilation or linking
     * process.
     * 
     * @return The info log
     */
    public String getShaderLog() {
        return log;
    }
}
