package com.teacore.resource;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 * This class provides I/O functions for reading text, image and binary resources, which are stored
 * in the project and thus later in the .jar file itself.
 * 
 * @author ShietStone
 */
public class ResourceLoader {

    private String targetPath;
    
    /**
     * Creates this object with the given target path, which indicates the working directory. The
     * target path will be appended by the given file paths in each load/write operation. It may be
     * null, in which case it will be set to be an empty String. Note that paths in resource space
     * need to start with a '/' forward slash.
     * 
     * @param targetPath The working directory of this loader
     */
    public ResourceLoader(String targetPath) {
        if(targetPath != null)
            this.targetPath = targetPath;
        else
            this.targetPath = "";
    }
    
    /**
     * Returns the target path aka. the working directory of this loader.
     * 
     * @return The working directory path of this loader
     */
    public String getTargetPath() {
        return targetPath;
    }
    
    /**
     * Loads a text resources content as a string. The path is relative to this loaders target path
     * as  specified by getTargetPath(). The path argument may not be null.
     * 
     * @param path The path to the resource to load (relative to the loaders target path)
     * @return The text resources content as String
     * @throws IOException If the resource cannot be loaded
     */
    public String loadTextResource(String path) throws IOException {
        if(path == null)
            throw new IllegalArgumentException("Path is null");
        
        String fullPath = targetPath + path;
        InputStream inputStream = getResourceInputStream(fullPath);
        
        if(inputStream == null)
            throw new FileNotFoundException("The resource " + fullPath + " cannot be loaded");
        
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        
        int input;
        while((input = bufferedReader.read()) != -1)
            stringBuilder.append((char) input);
        
        bufferedReader.close();
        return stringBuilder.toString();
    }
    
    /**
     * Loads an image resource as a BufferedImage defined by the given path, which is relative to
     * the loaders target path. The path argument may not be null.
     * 
     * @param path The path defining the resource to load
     * @return The image obtained from the resource
     * @throws IOException If the image cannot be loaded
     */
    public BufferedImage loadImageResource(String path) throws IOException {
        if(path == null)
            throw new IllegalArgumentException("Path is null");
        
        String fullPath = targetPath + path;
        InputStream inputStream = getResourceInputStream(fullPath);
        
        if(inputStream == null)
            throw new FileNotFoundException("The resource " + fullPath + " cannot be loaded");
        
        return ImageIO.read(inputStream);
    }
    
    /**
     * Loads a binary resource as byte array. The path is relative to this loaders target path and
     * may not be null.
     * 
     * @param path The path describing the resources location
     * @return The byte array containing the resources raw byte data
     * @throws IOException If the resource cannot be read
     */
    public byte[] loadBinaryResource(String path) throws IOException {
        if(path == null)
            throw new IllegalArgumentException("Path is null");
        
        String fullPath = targetPath + path;
        InputStream inputStream = getResourceInputStream(fullPath);
        
        if(inputStream == null)
            throw new FileNotFoundException("The resource " + fullPath + " cannot be loaded");
        
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        
        int input;
        while((input = bufferedReader.read()) != -1)
            byteArrayBuilder.append((byte) input);
        
        bufferedReader.close();
        return byteArrayBuilder.toByteArray();
    }
    
    private static InputStream getResourceInputStream(String path) {
        return ResourceLoader.class.getResourceAsStream(path);
    }
}
