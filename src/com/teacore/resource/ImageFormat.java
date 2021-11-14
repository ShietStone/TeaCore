package com.teacore.resource;

/**
 * Resembles different image formats and contains a string representation to use in ImageIO
 * operations.
 * 
 * @author ShietStone
 */
public enum ImageFormat {
    PNG("PNG"),
    JPEG("JPG");
    
    private String stringRepresentation;
    
    private ImageFormat(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }
    
    public String getStringRepresentation() {
        return stringRepresentation;
    }
}
