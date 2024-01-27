/*
 */
package com.mvdir;

import java.util.List;

/**
 *
 * @author manager
 */
public interface FileHandler {

    // Getters y setters
    public String getName();

    public String getContent();

    public List<FileHandler> getList();

    public boolean add(FileHandler inFile);

    public boolean write(String inBasePath);

}
