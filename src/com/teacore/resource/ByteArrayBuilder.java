package com.teacore.resource;

/**
 * A class to build byte arrays from a byte stream of unknown size. Inspired by the StringBuilder
 * class.
 * 
 * @author ShietStone
 */
public class ByteArrayBuilder {

    private static final int DEFAULT_ALLOCATION_SIZE = 1024;
    
    private int allocationSize;
    private int buildingArraySize;
    private byte[] buildingArray;
    private byte[][] builtArrays;
    
    /**
     * Creates the builder with the allocation size, which represents the size of the buffer that
     * will be allocated when the builder has no buffer left.
     * 
     * @param allocationSize The buffer size to allocate beforehand
     */
    public ByteArrayBuilder(int allocationSize) {
        if(allocationSize <= 0)
            throw new IllegalArgumentException("Allocation size must be greater than 0");
        
        this.allocationSize = allocationSize;
        resetBuildingArray();
        builtArrays = new byte[0][];
    }
    
    /**
     * Initializes the builder with a default allocation size.
     */
    public ByteArrayBuilder() {
        this(DEFAULT_ALLOCATION_SIZE);
    }
    
    /**
     * Appends the byte to the buffer and allocates more buffer if needed.
     * 
     * @param b The byte to append
     */
    public void append(byte b) {
        if(buildingArraySize >= buildingArray.length) {
            byte[][] nBuiltArrays = new byte[builtArrays.length + 1][];
            
            for(int index = 0; index < builtArrays.length; index++)
                nBuiltArrays[index] = builtArrays[index];
            
            nBuiltArrays[nBuiltArrays.length - 1] = buildingArray;
            builtArrays = nBuiltArrays;
            resetBuildingArray();
        }
        
        buildingArray[buildingArraySize++] = b;
    }
    
    /**
     * Returns the current size of buffered data (not the real size of the buffer).
     * 
     * @return The size of the stored data
     */
    public int length() {
        return builtArrays.length * allocationSize + buildingArraySize;
    }
    
    /**
     * Returns the buffered data in one byte array.
     * 
     * @return The data as one array
     */
    public byte[] toByteArray() {
        byte[] resultArray = new byte[length()];
        int resultArrayIndex = 0;
        
        for(byte[] builtArray : builtArrays)
            for(byte b : builtArray)
                resultArray[resultArrayIndex++] = b;
        
        for(int index = 0; index < buildingArraySize; index++)
            resultArray[resultArrayIndex++] = buildingArray[index];
        
        return resultArray;
    }
    
    private void resetBuildingArray() {
        buildingArray = new byte[allocationSize];
        buildingArraySize = 0;
    }
}
