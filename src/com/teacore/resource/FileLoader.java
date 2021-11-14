package com.teacore.resource;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 * This class provides I/O functions for reading/writing text, image and binary files.
 * 
 * @author ShietStone
 */
public class FileLoader {

    private String targetPath;
    
    /**
     * Creates this object with the given target path, which indicates the working directory. The
     * target path will be appended by the given file paths in each load/write operation. It may be
     * null, in which case it will be set to be an empty String.
     * 
     * @param targetPath The working directory of this loader
     */
    public FileLoader(String targetPath) {
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
     * Loads a text files content as a string. The path is relative to this loaders target path as 
     * specified by getTargetPath(). The path argument may not be null.
     * 
     * @param path The path to the file to load (relative to the loaders target path)
     * @return The text files content as String
     * @throws IOException If the file cannot be loaded
     */
    public String loadTextFile(String path) throws IOException {
        if(path == null)
            throw new IllegalArgumentException("Path is null");
        
        String fullPath = targetPath + path;
        File file = new File(fullPath);
        
        if(!file.exists())
            throw new FileNotFoundException("File " + fullPath + "does not exist");
        
        if(file.isDirectory())
            throw new IllegalFileException("File " + fullPath + " is a directory");
        
        FileInputStream inputStream = new FileInputStream(file);
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
     * Writes a String into a file specified by the given path (which is relative to this loaders 
     * target path). The path argument may not be null. If the file does not exist yet it will be
     * created. If the content is null nothing will be written, nut the file will still get 
     * created.
     * 
     * @param path The path to the file to write to (relative to the loaders target path)
     * @param content The content to write to the file
     * @param append If the content should overwrite or append the file
     * @throws IOException If the content could not be written to the file
     */
    public void writeTextFile(String path, String content, boolean append) throws IOException {
        if(path == null)
            throw new IllegalArgumentException("Path is null");
        
        if(content == null)
            content = "";
        
        String fullPath = targetPath + path;
        File file = new File(fullPath);
        file.createNewFile();
        
        if(file.isDirectory())
            throw new IllegalFileException("File " + fullPath + " is a directory");
        
        FileOutputStream outputStream = new FileOutputStream(file, append);
        
        for(int index = 0; index < content.length(); index++)
            outputStream.write(content.charAt(index));
        
        outputStream.flush();
        outputStream.close();
    }
    
    /**
     * Loads an image file as a BufferedImage defined by the given path, which is relative to the 
     * loaders target path. The path argument may not be null.
     * 
     * @param path The path defining the file to load
     * @return The image obtained from the file
     * @throws IOException If the image cannot be loaded
     */
    public BufferedImage loadImageFile(String path) throws IOException {
        if(path == null)
            throw new IllegalArgumentException("Path is null");
        
        String fullPath = targetPath + path;
        File file = new File(fullPath);
        
        if(!file.exists())
            throw new FileNotFoundException("File " + fullPath + "does not exist");
        
        if(file.isDirectory())
            throw new IllegalFileException("File " + fullPath + " is a directory");
        
        return ImageIO.read(file);
    }
    
    /**
     * Writes a BufferedImage to a file in a specified format. The path (relative to this loaders 
     * target path) content and image format may not be null. The data will be written using the 
     * given image format. If the file does not exist it will be created.
     * 
     * @param path The file location
     * @param content The image to write
     * @param imageFormat The format to write the image in
     * @throws IOException If the data cannot be written
     */
    public void writeImageFile(String path, BufferedImage content, ImageFormat imageFormat) throws IOException {
        if(path == null || content == null || imageFormat == null)
            throw new IllegalArgumentException("An argument is null");
       
        String fullPath = targetPath + path;
        File file = new File(fullPath);
        file.createNewFile();
        
        if(file.isDirectory())
            throw new IllegalFileException("File " + fullPath + " is a directory");
        
        ImageIO.write(content, imageFormat.getStringRepresentation(), file);
    }
    
    /**
     * Loads a binary file as byte array. The path is relative to this loaders target path and may
     * not be null.
     * 
     * @param path The path describing the files location
     * @return The byte array containing the files raw byte data
     * @throws IOException If the file cannot be read
     */
    public byte[] loadBinaryFile(String path) throws IOException {
        if(path == null)
            throw new IllegalArgumentException("Path is null");
        
        String fullPath = targetPath + path;
        File file = new File(fullPath);
        
        if(!file.exists())
            throw new FileNotFoundException("File " + fullPath + "does not exist");
        
        if(file.isDirectory())
            throw new IllegalFileException("File " + fullPath + " is a directory");
        
        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        
        int input;
        while((input = bufferedReader.read()) != -1)
            byteArrayBuilder.append((byte) input);
        
        bufferedReader.close();
        return byteArrayBuilder.toByteArray();
    }
    
    /**
     * Writes a byte array to a file, described by the given path, which is relative to this 
     * loaders target path and it may not be null. The append flag indicates if the content should 
     * be appended or overwrite the file. If the file does not exist yet it will be created. The
     * content may be null, in which case no content will be written (the file would still get
     * created).
     * 
     * @param path The path of the file
     * @param content The content to write
     * @param append If the content should append or overwrite the file
     * @throws IOException If the data cannot be written
     */
    public void writeBinaryFile(String path, byte[] content, boolean append) throws IOException {
        if(path == null)
            throw new IllegalArgumentException("Path is null");
        
        if(content == null)
            content = new byte[0];
        
        String fullPath = targetPath + path;
        File file = new File(fullPath);
        file.createNewFile();
        
        if(file.isDirectory())
            throw new IllegalFileException("File " + fullPath + " is a directory");
        
        FileOutputStream outputStream = new FileOutputStream(file, append);
        
        for(int index = 0; index < content.length; index++)
            outputStream.write(content[index]);
        
        outputStream.flush();
        outputStream.close();
    }
}
