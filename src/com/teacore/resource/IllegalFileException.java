package com.teacore.resource;

import java.io.IOException;

/**
 * Intended to be thrown if a file is not right e.g. if a text file is needed but a directory is
 * given.
 * 
 * @author ShietStone
 */
public class IllegalFileException extends IOException {

    private static final long serialVersionUID = 1L;

    /**
     * Create the exception using the given error message.
     * 
     * @param message The message to show
     */
    public IllegalFileException(String message) {
        super(message);
    }
}
